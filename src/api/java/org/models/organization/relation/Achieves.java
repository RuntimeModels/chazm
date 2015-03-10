package org.models.organization.relation;

import org.models.organization.Organization;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;

/**
 * The {@linkplain Achieves} interface defines the achieves relation of an {@linkplain Organization}.
 * 
 * @author Christopher Zhong
 * @see Role
 * @see SpecificationGoal
 * @since 7.0.0
 */
public interface Achieves {
	/**
	 * Returns the {@linkplain Role} of this {@linkplain Achieves}.
	 *
	 * @return the {@linkplain Role} of this {@linkplain Achieves}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain SpecificationGoal} of this {@linkplain Achieves}.
	 *
	 * @return the {@linkplain SpecificationGoal} of this {@linkplain Achieves}.
	 */
	SpecificationGoal getSpecificationGoal();
}