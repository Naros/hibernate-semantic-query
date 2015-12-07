/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression;

import java.math.BigInteger;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.domain.BasicType;

/**
 * @author Steve Ebersole
 */
public class LiteralBigIntegerExpression extends AbstractLiteralExpressionImpl<BigInteger> {
	public LiteralBigIntegerExpression(BigInteger value, BasicType<BigInteger> typeDescriptor) {
		super( value, typeDescriptor );
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitLiteralBigIntegerExpression( this );
	}

	@Override
	protected void validateInferredType(Class javaType) {
		if ( !BigInteger.class.equals( javaType ) ) {
			throw new TypeInferenceException( "Inferred type [" + javaType + "] was not convertible to BigInteger" );
		}
	}
}
