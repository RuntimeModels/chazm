/*
 * Assignment.java
 *
 * Created on Dec 6, 2005
 *
 * See License.txt file the license agreement.
 */
package model.organization.relations;

import model.organization.entities.Agent;
import model.organization.entities.InstanceGoal;
import model.organization.entities.Role;
import model.organization.id.AbstractId;
import model.organization.id.UniqueId;

/**
 * The {@linkplain AssignmentRelation} class is an implementation of the {@linkplain Assignment}.
 *
 * @author Christopher Zhong
 * @see Assignment
 * @since 2.0
 */
public class AssignmentRelation implements Assignment {
	/**
	 * The {@linkplain Id} extends the {@link AbstractId} by using three {@link UniqueId}s; the {@linkplain UniqueId} of an {@linkplain Agent}, the
	 * {@linkplain UniqueId} of a {@linkplain Role}, and the {@linkplain UniqueId} of an {@linkplain InstanceGoal}.
	 *
	 * @author Christopher Zhong
	 * @see UniqueId
	 * @since 4.0
	 */
	public static class Id extends AbstractId<Assignment> {
		/**
		 * Serial version ID.
		 */
		private static final long serialVersionUID = 7696865567559985410L;

		/**
		 * Internal <code>String</code> for the formatting of <code>Assignment</code> class.
		 */
		private static final String STRING_FORMAT = "%s, %s, %s";

		/**
		 * The {@linkplain Id} that represents an {@linkplain Agent}.
		 */
		private final UniqueId<Agent> agentId;

		/**
		 * The {@linkplain Id} that represents a {@linkplain Role}.
		 */
		private final UniqueId<Role> roleId;

		/**
		 * The {@linkplain Id} that represents an {@linkplain InstanceGoal}.
		 */
		private final UniqueId<InstanceGoal> goalId;

		/**
		 * Optimization for hash code computation since it never changes.
		 */
		private transient Integer hashCode = null;

		/**
		 * Optimization for <code>toString</code> method since it never changes.
		 */
		private transient String toString = null;

		/**
		 * Constructs a new instance of {@linkplain Assignment}.
		 *
		 * @param agentId
		 *            the {@linkplain Id} that represents an {@linkplain Agent}.
		 * @param roleId
		 *            the {@linkplain Id} that represents a {@linkplain Role}.
		 * @param goalId
		 *            the {@linkplain Id} that represents an {@linkplain InstanceGoal}.
		 */
		public Id(final UniqueId<Agent> agentId, final UniqueId<Role> roleId, final UniqueId<InstanceGoal> goalId) {
			super(Assignment.class);
			this.agentId = agentId;
			this.roleId = roleId;
			this.goalId = goalId;
		}

		/**
		 * Returns the {@linkplain Id} that represents an {@linkplain Agent}.
		 *
		 * @return the {@linkplain Id} that represents an {@linkplain Agent}.
		 */
		private UniqueId<Agent> getAgentId() {
			return agentId;
		}

		/**
		 * Returns the {@linkplain Id} that represents a {@linkplain Role}.
		 *
		 * @return the {@linkplain Id} that represents a {@linkplain Role}.
		 */
		private UniqueId<Role> getRoleId() {
			return roleId;
		}

		/**
		 * Returns the {@linkplain Id} that represents an {@linkplain InstanceGoal}.
		 *
		 * @return the {@linkplain Id} that represents an {@linkplain InstanceGoal}.
		 */
		private UniqueId<InstanceGoal> getGoalId() {
			return goalId;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof Id) {
				final Id id = (Id) object;
				return getAgentId().equals(id.getAgentId()) && getRoleId().equals(id.getRoleId()) && getGoalId().equals(id.getGoalId());
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hashCode == null) {
				hashCode = getAgentId().hashCode() << 22 | getRoleId().hashCode() << 11 | getGoalId().hashCode();
			}
			return hashCode;
		}

		@Override
		public String toString() {
			if (toString == null) {
				toString = String.format(STRING_FORMAT, getAgentId(), getRoleId(), getGoalId());
			}
			return toString;
		}
	}

	/**
	 * Internal <code>String</code> for the formatting of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT_PARAMETERS = "<%s (%s)>";

	/**
	 * Internal <code>String</code> for the formatting of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT_NO_PARAMETERS = "<%s>";

	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Assignment}.
	 */
	private final UniqueId<Assignment> id;

	/**
	 * The {@linkplain Agent} of this {@linkplain Assignment}.
	 */
	private final Agent agent;

	/**
	 * The {@linkplain Role} of this {@linkplain Assignment}.
	 */
	private final Role role;

	/**
	 * The {@linkplain InstanceGoal} of this {@linkplain Assignment}.
	 */
	private final InstanceGoal goal;

	/**
	 * Constructs a new instance of {@linkplain Assignment}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of this {@linkplain Assignment}.
	 * @param role
	 *            the {@linkplain Role} of this {@linkplain Assignment}.
	 * @param goal
	 *            the {@linkplain InstanceGoal} of this {@linkplain Assignment}.
	 */
	public AssignmentRelation(final Agent agent, final Role role, final InstanceGoal goal) {
		if (agent == null) {
			throw new IllegalArgumentException("Parameter (agent) cannot be null");
		}
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		if (goal == null) {
			throw new IllegalArgumentException("Parameter (goal) cannot be null");
		}
		id = new Id(agent.getId(), role.getId(), goal.getId());
		this.agent = agent;
		this.role = role;
		this.goal = goal;
	}

	@Override
	public final UniqueId<Assignment> getId() {
		return id;
	}

	@Override
	public Agent getAgent() {
		return agent;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public InstanceGoal getGoal() {
		return goal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Assignment) {
			final Assignment assignment = (Assignment) object;
			return getId().equals(assignment.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getGoal().getParameter() == null ? String.format(STRING_FORMAT_NO_PARAMETERS, getId()) : String.format(STRING_FORMAT_PARAMETERS, getId(),
				getGoal().getParameter());
	}

}
