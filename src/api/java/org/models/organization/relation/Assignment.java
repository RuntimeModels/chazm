package org.models.organization.relation;

import org.models.organization.Organization;
import org.models.organization.entity.Agent;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Role;
import org.models.organization.identifier.Identifiable;

/**
 * The {@linkplain Assignment} interface defines the assignment relation, which means that an {@linkplain Agent} is assigned to play a {@linkplain Role} to
 * achieve an {@linkplain InstanceGoal}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @see Agent
 * @see Role
 * @see InstanceGoal
 * @since 7.0.0
 */
public interface Assignment extends Identifiable {
	/**
	 * Returns the {@linkplain Agent} of this {@linkplain Assignment}.
	 *
	 * @return the {@linkplain Agent} of this {@linkplain Assignment}.
	 */
	Agent getAgent();

	/**
	 * Returns the {@linkplain Role} of this {@linkplain Assignment}.
	 *
	 * @return the {@linkplain Role} of this {@linkplain Assignment}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain InstanceGoal} of this {@linkplain Assignment}.
	 *
	 * @return the {@linkplain InstanceGoal} of this {@linkplain Assignment}.
	 */
	InstanceGoal<?> getGoal();
}