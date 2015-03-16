/*
 * Task.java
 *
 * Created on May 17, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.relations;

import java.util.Collection;

import message.M;
import model.organization.entities.Agent;
import model.organization.entities.InstanceGoal;
import model.organization.entities.Role;
import model.organization.entities.SpecificationGoal;
import model.organization.functions.RoleGoodnessFunction;
import model.organization.id.AbstractId;
import model.organization.id.UniqueId;

/**
 * The {@linkplain TaskRelation} class is an implementation of the {@linkplain Task}.
 *
 * @author Christopher Zhong
 * @see Task
 * @since 6.0
 */
public class TaskRelation implements Task {
	/**
	 * The {@linkplain Id} extends the {@linkplain AbstractId} by using two {@linkplain UniqueId}s; the {@linkplain UniqueId} of a {@linkplain Role} and the
	 * {@linkplain UniqueId} of a {@linkplain SpecificationGoal}.
	 *
	 * @author Christopher Zhong
	 * @see UniqueId
	 * @since 6.0
	 */
	public static class Id extends AbstractId<Task> {
		/**
		 * Serial version ID.
		 */
		private static final long serialVersionUID = 7250923848097712441L;

		/**
		 * Internal <code>String</code> for the formatting of <code>Task</code> class.
		 */
		private static final String STRING_FORMAT = "%s, %s";

		/**
		 * The {@linkplain Id} of the {@linkplain Role}.
		 */
		private final UniqueId<Role> roleId;

		/**
		 * The {@linkplain Id} of the {@linkplain SpecificationGoal}.
		 */
		private final UniqueId<SpecificationGoal> goalId;

		/**
		 * Optimization for hash code computation since it never changes.
		 */
		private transient Integer hashCode = null;

		/**
		 * Optimization for <code>toString</code> method since it never changes.
		 */
		private transient String toString = null;

		/**
		 * Constructs a new instance of {@linkplain Id}.
		 *
		 * @param roleId
		 *            the {@linkplain Id} of the {@linkplain Role}.
		 * @param goalId
		 *            the {@linkplain Id} of the {@linkplain SpecificationGoal}.
		 */
		public Id(final UniqueId<Role> roleId, final UniqueId<SpecificationGoal> goalId) {
			super(Task.class);
			if (roleId == null) {
				throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "roleId"));
			}
			if (goalId == null) {
				throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "goalId"));
			}
			this.roleId = roleId;
			this.goalId = goalId;
		}

		/**
		 * Returns the {@linkplain Id} of the {@linkplain Role}.
		 *
		 * @return the {@linkplain Id} of the {@linkplain Role}.
		 */
		private UniqueId<Role> getRoleId() {
			return roleId;
		}

		/**
		 * Returns the {@linkplain Id} of the {@linkplain SpecificationGoal}.
		 *
		 * @return the {@linkplain Id} of the {@linkplain SpecificationGoal}.
		 */
		private UniqueId<SpecificationGoal> getGoalId() {
			return goalId;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof Id) {
				final Id id = (Id) object;
				return getRoleId().equals(id.getRoleId()) && getGoalId().equals(id.getGoalId());
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hashCode == null) {
				hashCode = getRoleId().hashCode() << 16 | getGoalId().hashCode();
			}
			return hashCode;
		}

		@Override
		public String toString() {
			if (toString == null) {
				toString = String.format(STRING_FORMAT, getRoleId(), getGoalId());
			}
			return toString;
		}
	}

	/**
	 * Internal <code>String</code> for the formatting of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "<%s>";

	/**
	 * The {@linkplain UniqueId} of this {@linkplain Task}.
	 */
	private final UniqueId<Task> id;

	/**
	 * The {@linkplain Role} of this {@linkplain Task}.
	 */
	private final Role role;

	/**
	 * The {@linkplain SpecificationGoal} of this {@linkplain Task}.
	 */
	private final SpecificationGoal goal;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of {@linkplain Task}.
	 *
	 * @param role
	 *            the {@linkplain Role} of this {@linkplain Task}.
	 * @param goal
	 *            the {@linkplain SpecificationGoal} of this {@linkplain Task}.
	 */
	public TaskRelation(final Role role, final SpecificationGoal goal) {
		if (role == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "role"));
		}
		if (goal == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "specificationGoal"));
		}
		id = new Id(role.getId(), goal.getId());
		this.role = role;
		this.goal = goal;
	}

	@Override
	public final UniqueId<Task> getId() {
		return id;
	}

	@Override
	public final Role getRole() {
		return role;
	}

	@Override
	public final SpecificationGoal getGoal() {
		return goal;
	}

	/**
	 * Returns the score of how good the given {@link Agent} is able to carry out the associated {@link TaskRelation} with respect to the given
	 * {@link InstanceGoal}. The given {@link InstanceGoal} must be an instance of the {@link SpecificationGoal} of the {@link TaskRelation}.
	 *
	 * @param agent
	 *            the {@link Agent} to compute the goodness score.
	 * @param instanceGoal
	 *            the {@link InstanceGoal} that may be used in computing the goodness score.
	 * @param assignments
	 *            the {@link Assignment} that may be used in computing the goodness score.
	 * @return the goodness score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how good the given {@link Agent} is able to carry out the associated
	 *         {@link TaskRelation} with respect to the given {@link InstanceGoal}. A value of <code>0.0</code> indicates that the {@link Agent} cannot perform
	 *         the {@link TaskRelation} or the given {@link InstanceGoal} is not an instance of the {@link SpecificationGoal} of this {@link TaskRelation}.
	 * @see RoleGoodnessFunction#goodness(Role, Agent, InstanceGoal, Collection)
	 */
	public final double goodness(final Agent agent, final InstanceGoal instanceGoal, final Collection<Assignment> assignments) {
		if (getGoal().equals(instanceGoal.getSpecificationGoal())) {
			return getRole().goodness(agent, instanceGoal, assignments);
		}
		return RoleGoodnessFunction.MIN_SCORE;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Task) {
			final Task task = (Task) object;
			return getId().equals(task.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getId());
		}
		return toString;
	}

}
