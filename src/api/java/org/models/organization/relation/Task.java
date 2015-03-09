/*
 * Task.java
 *
 * Created on May 17, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.Agent;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.PerformanceFunction;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.function.RoleGoodnessFunction;
import org.models.organization.identifier.UniqueId;

/**
 * The <code>Task</code> class represents the task entity of the Organization Model.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public class Task {

	/**
	 * Internal <code>String</code> for the formatting of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "<%s>";

	/**
	 * The <code>TaskIdentifier</code> extends the {@link UniqueId} by using two {@link UniqueId} as the form of identification.
	 *
	 * @author Christopher Zhong
	 * @see UniqueId
	 * @since 6.0
	 */
	private static class TaskIdentifier extends UniqueId {

		/**
		 * Default serial version ID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Internal <code>String</code> for the formatting of <code>Task</code> class.
		 */
		private static final String STRING_FORMAT = "%s, %s";

		/**
		 * The <code>UniqueIdentifier</code> of the <code>Role</code>.
		 */
		private final UniqueId roleIdentifier;

		/**
		 * The <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 */
		private final UniqueId goalIdentifier;

		/**
		 * Optimization for hash code computation since it never changes.
		 */
		private transient Integer hashCode = null;

		/**
		 * Optimization for <code>toString</code> method since it never changes.
		 */
		private transient String toString = null;

		/**
		 * Constructs a new instance of <code>AssignmentIdentifier</code>.
		 *
		 * @param roleIdentifier
		 *            the <code>UniqueIdentifier</code> of the <code>Role</code> .
		 * @param goalIdentifier
		 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 */
		public TaskIdentifier(final UniqueId roleIdentifier, final UniqueId goalIdentifier) {
			this.roleIdentifier = roleIdentifier;
			this.goalIdentifier = goalIdentifier;
		}

		/**
		 * Returns the <code>UniqueIdentifier</code> of the <code>Role</code>.
		 *
		 * @return the <code>UniqueIdentifier</code> of the <code>Role</code>..
		 */
		private UniqueId getRoleIdentifier() {
			return roleIdentifier;
		}

		/**
		 * Returns the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 *
		 * @return the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 */
		private UniqueId getGoalIdentifier() {
			return goalIdentifier;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof TaskIdentifier) {
				final TaskIdentifier taskIdentifier = (TaskIdentifier) object;
				return getRoleIdentifier().equals(taskIdentifier.getRoleIdentifier()) && getGoalIdentifier().equals(taskIdentifier.getGoalIdentifier());
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hashCode == null) {
				hashCode = getRoleIdentifier().hashCode() << 16 | getGoalIdentifier().hashCode();
			}
			return hashCode;
		}

		@Override
		public String toString() {
			if (toString == null) {
				toString = String.format(STRING_FORMAT, getRoleIdentifier(), getGoalIdentifier());
			}
			return toString;
		}

	}

	/**
	 * The <code>UniqueIdentifier</code> of the <code>Task</code>.
	 */
	private final UniqueId identifier;

	/**
	 * The <code>Role</code> of this <code>Task</code>.
	 */
	private final Role role;

	/**
	 * The <code>SpecificationGoal</code> of this <code>Task</code>.
	 */
	private final SpecificationGoal specificationGoal;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>TaskImpl</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>Task</code>.
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> of this <code>Task</code>.
	 */
	public Task(final Role role, final SpecificationGoal specificationGoal) {
		if (role == null || specificationGoal == null) {
			throw new IllegalArgumentException(String.format("Parameters (role: %s, goal: %s) cannot be null", role, specificationGoal));
		}
		identifier = new TaskIdentifier(role.getIdentifier(), specificationGoal.getIdentifier());
		this.role = role;
		this.specificationGoal = specificationGoal;
	}

	/**
	 * Returns the unique <code>UniqueIdentifier</code> representing the <code>SimpleTask</code>.
	 *
	 * @return the unique <code>UniqueIdentifier</code> representing the <code>SimpleTask</code>.
	 */
	public final UniqueId getIdentifier() {
		return identifier;
	}

	/**
	 * Returns the <code>Role</code> of this <code>Task</code>.
	 *
	 * @return the <code>Role</code> of this <code>Task</code>.
	 */
	public final Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>SpecificationGoal</code> of this <code>Task</code>.
	 *
	 * @return the <code>SpecificationGoal</code> of this <code>Task</code>.
	 */
	public final SpecificationGoal getSpecificationGoal() {
		return specificationGoal;
	}

	/**
	 * Returns the set of <code>PerformanceFunction</code> that is linked to this <code>Task</code>.
	 *
	 * @return the set of <code>PerformanceFunction</code> that is linked to this <code>Task</code>.
	 */
	public final Set<PerformanceFunction> getLinkedSet() {
		return getRole().getUsesSet();
	}

	/**
	 * Returns the score of how good the given {@link Agent} is able to carry out the associated {@link Task} with respect to the given {@link InstanceGoal}.
	 * The given {@link InstanceGoal} must be an instance of the {@link SpecificationGoal} of the {@link Task}.
	 *
	 * @param agent
	 *            the {@link Agent} to compute the goodness score.
	 * @param instanceGoal
	 *            the {@link InstanceGoal} that may be used in computing the goodness score.
	 * @param assignments
	 *            the {@link Assignment} that may be used in computing the goodness score.
	 * @return the goodness score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how good the given {@link Agent} is able to carry out the associated
	 *         {@link Task} with respect to the given {@link InstanceGoal}. A value of <code>0.0</code> indicates that the {@link Agent} cannot perform the
	 *         {@link Task} or the given {@link InstanceGoal} is not an instance of the {@link SpecificationGoal} of this {@link Task}.
	 * @see RoleGoodnessFunction#goodness(Role, Agent, InstanceGoal, Collection)
	 */
	public final double goodness(final Agent agent, final InstanceGoal<?> instanceGoal, final Collection<Assignment> assignments) {
		if (getSpecificationGoal().equals(instanceGoal.getSpecificationGoal())) {
			return getRole().goodness(agent, instanceGoal, assignments);
		}
		return RoleGoodnessFunction.MIN_SCORE;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Task) {
			final Task task = (Task) object;
			return getIdentifier().equals(task.getIdentifier());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getIdentifier());
		}
		return toString;
	}

}
