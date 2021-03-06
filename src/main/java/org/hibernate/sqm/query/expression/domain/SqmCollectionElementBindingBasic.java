/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sqm.query.expression.domain;

import org.hibernate.sqm.NotYetImplementedException;
import org.hibernate.sqm.SemanticQueryWalker;

/**
 * @author Steve Ebersole
 */
public class SqmCollectionElementBindingBasic extends AbstractSqmCollectionElementBinding implements
		SqmCollectionElementBinding {
	public SqmCollectionElementBindingBasic(SqmPluralAttributeBinding pluralAttributeBinding) {
		super( pluralAttributeBinding );
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		throw new NotYetImplementedException(  );
	}
}
