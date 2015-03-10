package org.models.organization.relation;

import org.models.organization.entity.PerformanceFunction;
import org.models.organization.entity.Role;

/**
 * Represents the uses relation, where a {@link Role} uses a {@link PerformanceFunction}
 * 
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Uses {
	/**
	 * Returns {@linkplain Role} of this {@linkplain Uses}.
	 *
	 * @return {@linkplain Role} of this {@linkplain Uses}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain PerformanceFunction} of this {@linkplain Uses}.
	 *
	 * @return the {@linkplain PerformanceFunction} of this {@linkplain Uses}.
	 */
	PerformanceFunction getPerformanceFunction();
}