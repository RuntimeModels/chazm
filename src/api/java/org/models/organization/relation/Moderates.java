package org.models.organization.relation;

import org.models.organization.Organization;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.PerformanceFunction;

/**
 * The {@linkplain Moderates} interface defines the moderates relation, where a {@linkplain PerformanceFunction} moderates an {@linkplain Attribute}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Moderates {
	/**
	 * Returns the {@linkplain PerformanceFunction} of this {@linkplain Moderates}.
	 *
	 * @return the {@linkplain PerformanceFunction} of this {@linkplain Moderates}.
	 */
	PerformanceFunction getPerformanceFunction();

	/**
	 * Returns the {@linkplain Attribute} of this {@linkplain Moderates}.
	 *
	 * @return the {@linkplain Attribute} of this {@linkplain Moderates}.
	 */
	Attribute getAttribute();
}