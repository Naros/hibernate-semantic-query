/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.orm.type.descriptor.java.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Comparator;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.BinaryStream;
import org.hibernate.engine.jdbc.BlobImplementer;
import org.hibernate.engine.jdbc.BlobProxy;
import org.hibernate.engine.jdbc.WrappedBlob;
import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;

import org.hibernate.orm.type.descriptor.java.spi.AbstractBasicTypeDescriptor;
import org.hibernate.orm.type.descriptor.java.spi.IncomparableComparator;
import org.hibernate.orm.type.descriptor.java.spi.MutabilityPlan;
import org.hibernate.orm.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.orm.type.descriptor.spi.WrapperOptions;
import org.hibernate.orm.type.descriptor.sql.spi.SqlTypeDescriptor;

/**
 * Descriptor for {@link Blob} handling.
 * <p/>
 * Note, {@link Blob blobs} really are mutable (their internal state can in fact be mutated).  We simply
 * treat them as immutable because we cannot properly check them for changes nor deep copy them.
 *
 * @author Steve Ebersole
 * @author Brett Meyer
 */
public class BlobJavaDescriptor extends AbstractBasicTypeDescriptor<Blob> {
	public static final BlobJavaDescriptor INSTANCE = new BlobJavaDescriptor();

	public static class BlobMutabilityPlan implements MutabilityPlan<Blob> {
		public static final BlobMutabilityPlan INSTANCE = new BlobMutabilityPlan();

		@Override
		public boolean isMutable() {
			return false;
		}

		@Override
		public Blob deepCopy(Blob value) {
			return value;
		}

		@Override
		public Serializable disassemble(Blob value) {
			throw new UnsupportedOperationException( "Blobs are not cacheable" );
		}

		@Override
		public Blob assemble(Serializable cached) {
			throw new UnsupportedOperationException( "Blobs are not cacheable" );
		}
	}

	public BlobJavaDescriptor() {
		super( Blob.class, BlobMutabilityPlan.INSTANCE );
	}

	@Override
	public String toString(Blob value) {
		final byte[] bytes;
		try {
			bytes = DataHelper.extractBytes( value.getBinaryStream() );
		}
		catch ( SQLException e ) {
			throw new HibernateException( "Unable to access blob stream", e );
		}
		return PrimitiveByteArrayJavaDescriptor.INSTANCE.toString( bytes );
	}

	@Override
	public Blob fromString(String string) {
		return BlobProxy.generateProxy( PrimitiveByteArrayJavaDescriptor.INSTANCE.fromString( string ) );
	}

	@Override
	public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
		return context.getTypeConfiguration().getSqlTypeDescriptorRegistry().getDescriptor( Types.BLOB );
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public Comparator<Blob> getComparator() {
		return IncomparableComparator.INSTANCE;
	}

	@Override
	public int extractHashCode(Blob value) {
		return System.identityHashCode( value );
	}

	@Override
	public boolean areEqual(Blob one, Blob another) {
		return one == another;
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public <X> X unwrap(Blob value, Class<X> type, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}

		try {
			if ( BinaryStream.class.isAssignableFrom( type ) ) {
				if ( BlobImplementer.class.isInstance( value ) ) {
					// if the incoming Blob is a wrapper, just pass along its BinaryStream
					return (X) ( (BlobImplementer) value ).getUnderlyingStream();
				}
				else {
					// otherwise we need to build a BinaryStream...
					return (X) new BinaryStreamImpl( DataHelper.extractBytes( value.getBinaryStream() ) );
				}
			}
			else if ( byte[].class.isAssignableFrom( type )) {
				if ( BlobImplementer.class.isInstance( value ) ) {
					// if the incoming Blob is a wrapper, just grab the bytes from its BinaryStream
					return (X) ( (BlobImplementer) value ).getUnderlyingStream().getBytes();
				}
				else {
					// otherwise extract the bytes from the stream manually
					return (X) DataHelper.extractBytes( value.getBinaryStream() );
				}
			}
			else if (Blob.class.isAssignableFrom( type )) {
				final Blob blob =  WrappedBlob.class.isInstance( value )
						? ( (WrappedBlob) value ).getWrappedBlob()
						: value;
				return (X) blob;
			}
		}
		catch ( SQLException e ) {
			throw new HibernateException( "Unable to access blob stream", e );
		}
		
		throw unknownUnwrap( type );
	}

	@Override
	public <X> Blob wrap(X value, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}

		// Support multiple return types from
		// org.hibernate.type.descriptor.sql.BlobTypeDescriptor
		if ( Blob.class.isAssignableFrom( value.getClass() ) ) {
			return options.getLobCreator().wrap( (Blob) value );
		}
		else if ( byte[].class.isAssignableFrom( value.getClass() ) ) {
			return options.getLobCreator().createBlob( ( byte[] ) value);
		}
		else if ( InputStream.class.isAssignableFrom( value.getClass() ) ) {
			InputStream inputStream = ( InputStream ) value;
			try {
				return options.getLobCreator().createBlob( inputStream, inputStream.available() );
			}
			catch ( IOException e ) {
				throw unknownWrap( value.getClass() );
			}
		}

		throw unknownWrap( value.getClass() );
	}
}
