package io.github.chriszhong.model.organization.relation;

import io.github.chriszhong.model.organization.Organization;
import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.entity.SpecificationGoal;

/**
 * The {@linkplain Achieves} interface defines the achieves relation, which means that a {@linkplain Role} achieves a {@linkplain SpecificationGoal}, of an
 * {@linkplain Organization}.
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
	SpecificationGoal getGoal();
}