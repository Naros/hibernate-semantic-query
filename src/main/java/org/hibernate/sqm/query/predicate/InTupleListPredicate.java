/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.predicate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.sqm.Helper;
import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.query.expression.Expression;

/**
 * @author Steve Ebersole
 */
public class InTupleListPredicate implements InPredicate {
	private final Expression testExpression;
	private final List<Expression> tupleListExpressions;
	private final boolean negated;

	public InTupleListPredicate(Expression testExpression) {
		this( testExpression, new ArrayList<Expression>() );
	}

	public InTupleListPredicate(Expression testExpression, Expression... tupleListExpressions) {
		this( testExpression, Helper.toExpandableList( tupleListExpressions ) );
	}

	public InTupleListPredicate(
			Expression testExpression,
			List<Expression> tupleListExpressions) {
		this( testExpression, tupleListExpressions, false );
	}

	public InTupleListPredicate(
			Expression testExpression,
			List<Expression> tupleListExpressions,
			boolean negated) {
		this.testExpression = testExpression;
		this.tupleListExpressions = tupleListExpressions;
		this.negated = negated;
	}

	@Override
	public Expression getTestExpression() {
		return testExpression;
	}

	public List<Expression> getTupleListExpressions() {
		return tupleListExpressions;
	}

	public void addExpression(Expression expression) {
		tupleListExpressions.add( expression );
	}

	@Override
	public boolean isNegated() {
		return negated;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitInTupleListPredicate( this );
	}
}
