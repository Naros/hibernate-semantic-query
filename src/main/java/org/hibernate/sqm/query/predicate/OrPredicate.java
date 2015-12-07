/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.predicate;

import org.hibernate.sqm.SemanticQueryWalker;

/**
 * @author Steve Ebersole
 */
public class OrPredicate implements Predicate {
	private final Predicate leftHandPredicate;
	private final Predicate rightHandPredicate;

	public OrPredicate(Predicate leftHandPredicate, Predicate rightHandPredicate) {
		this.leftHandPredicate = leftHandPredicate;
		this.rightHandPredicate = rightHandPredicate;
	}

	public Predicate getLeftHandPredicate() {
		return leftHandPredicate;
	}

	public Predicate getRightHandPredicate() {
		return rightHandPredicate;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitOrPredicate( this );
	}
}
