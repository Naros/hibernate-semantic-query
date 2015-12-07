/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.parser.internal.hql.path;

import org.hibernate.sqm.parser.internal.hql.antlr.HqlParser;
import org.hibernate.sqm.path.AttributePathPart;

/**
 * Strategy for resolving attribute path expressions in a contextually pluggable manner.
 *
 * @author Steve Ebersole
 */
public interface AttributePathResolver {
	/**
	 * Resolve the given path, returning {@code null} if the initial parts do not indicate the
	 * path is an attribute path.
	 *
	 * @param path The path tree to resolve
	 *
	 * @return The resolve path, or {@code null}.
	 */
	AttributePathPart resolvePath(HqlParser.DotIdentifierSequenceContext path);
}
