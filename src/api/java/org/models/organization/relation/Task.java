package org.models.organization.relation;

import org.models.organization.Organization;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.Identifiable;

/**
 * The {@linkplain Task} interface defines a task, which is a pair of a {@linkplain Role} and a {@linkplain SpecificationGoal}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Task extends Identifiable {
	/**
	 * Returns the {@linkplain Role} of this {@linkplain Task}.
	 *
	 * @return the {@linkplain Role} of this {@linkplain Task}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain SpecificationGoal} of this {@linkplain Task}.
	 *
	 * @return the {@linkplain SpecificationGoal} of this {@linkplain Task}.
	 */
	SpecificationGoal getSpecificationGoal();
}