package model.organization.relation;

import model.organization.entity.Agent;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Role;

/**
 * The {@linkplain AssignmentFactory} interface defines the API for constructing {@linkplain Assignment}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AssignmentFactory {

	/**
	 * Constructs an {@linkplain Assignment}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of the {@linkplain Assignment}.
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Assignment}.
	 * @param goal
	 *            the {@linkplain InstanceGoal} of the {@linkplain Assignment}.
	 * @return an {@linkplain Assignment}.
	 */
	Assignment buildAssignment(Agent agent, Role role, InstanceGoal goal);

}