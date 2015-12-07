/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query;

/**
 * @author Steve Ebersole
 */
public interface Parameter {
	/**
	 * If this represents a named parameter, return that parameter name;
	 * otherwise return {@code null}.
	 *
	 * @return The parameter name, or {@code null} if not a named parameter
	 */
	String getName();

	/**
	 * If this represents a positional parameter, return that parameter position;
	 * otherwise return {@code null}.
	 *
	 * @return The parameter position
	 */
	Integer getPosition();
}
