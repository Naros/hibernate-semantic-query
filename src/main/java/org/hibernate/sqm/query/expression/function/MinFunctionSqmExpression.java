/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.expression.function;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.domain.type.SqmDomainTypeBasic;
import org.hibernate.sqm.query.expression.SqmExpression;

/**
 * @author Steve Ebersole
 */
public class MinFunctionSqmExpression
		extends AbstractAggregateFunctionSqmExpression
		implements AggregateFunctionSqmExpression {
	public static final String NAME = "min";

	public MinFunctionSqmExpression(SqmExpression argument, boolean distinct, SqmDomainTypeBasic resultType) {
		super( argument, distinct, resultType );
	}

	@Override
	public String getFunctionName() {
		return NAME;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitMinFunction( this );
	}

	@Override
	public String asLoggableText() {
		return "MIN(" + getArgument().asLoggableText() + ")";
	}
}
