package model.organization.relations;

import model.organization.Organization;
import model.organization.entities.Role;
import model.organization.entities.SpecificationGoal;
import model.organization.id.Identifiable;

/**
 * The {@linkplain Task} interface defines a task, which is a pair of a {@linkplain Role} and a {@linkplain SpecificationGoal}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Task extends Identifiable<Task> {
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
	SpecificationGoal getGoal();
}