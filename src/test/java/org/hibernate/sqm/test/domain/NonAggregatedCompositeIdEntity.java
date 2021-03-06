/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sqm.test.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author Steve Ebersole
 */
@Entity
@IdClass( NonAggregatedCompositeIdEntity.LookupKey.class )
public class NonAggregatedCompositeIdEntity {
	public static class LookupKey implements Serializable {
		String system;
		Integer code;
	}

	@Id
	String system;
	@Id
	Integer code;

	String name;
}
