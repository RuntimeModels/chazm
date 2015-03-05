/*
 * Assignment.java
 *
 * Created on Dec 6, 2005
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.entity.Agent;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Role;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>Assignment</code> class represents an assignment, which is a tuple relation of {@link Agent}s, {@link Role}s, and {@link InstanceGoal}s.
 * <p>
 * An assignment means that an {@link Agent} is assigned to play a {@link Role} to achieve a {@link InstanceGoal}
 *
 * @author Christopher Zhong
 * @version $Revision: 1.5.2.2 $, $Date: 2010/06/24 19:26:07 $
 * @see Agent
 * @see Role
 * @see InstanceGoal
 * @since 2.0
 */
public class Assignment {

	/**
	 * The <code>AssignmentIdentifier</code> extends the {@link UniqueIdentifier} by using three {@link UniqueIdentifier} as the form of identification.
	 *
	 * @author Christopher Zhong
	 * @version $Revision: 1.5.2.2 $, $Date: 2010/06/24 19:26:07 $
	 * @see UniqueIdentifier
	 * @since 4.0
	 */
	private static class AssignmentIdentifier extends UniqueIdentifier {

		/**
		 * Default serial version ID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Internal <code>String</code> for the formatting of <code>Assignment</code> class.
		 */
		private static final String STRING_FORMAT = "%s, %s, %s";

		/**
		 * The <code>UniqueIdentifier</code> of the <code>Agent</code>.
		 */
		private final UniqueIdentifier agentIdentifier;

		/**
		 * The <code>UniqueIdentifier</code> of the <code>Role</code>.
		 */
		private final UniqueIdentifier roleIdentifier;

		/**
		 * The <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 */
		private final UniqueIdentifier goalIdentifier;

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
		 * @param agentIdentifier
		 *            the <code>UniqueIdentifier</code> of the <code>Agent</code>.
		 * @param roleIdentifier
		 *            the <code>UniqueIdentifier</code> of the <code>Role</code> .
		 * @param goalIdentifier
		 *            the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 */
		public AssignmentIdentifier(final UniqueIdentifier agentIdentifier, final UniqueIdentifier roleIdentifier, final UniqueIdentifier goalIdentifier) {
			this.agentIdentifier = agentIdentifier;
			this.roleIdentifier = roleIdentifier;
			this.goalIdentifier = goalIdentifier;
		}

		/**
		 * Returns the <code>UniqueIdentifier</code> of the <code>Agent</code>.
		 *
		 * @return the <code>UniqueIdentifier</code> of the <code>Agent</code>.
		 */
		private UniqueIdentifier getAgentIdentifier() {
			return agentIdentifier;
		}

		/**
		 * Returns the <code>UniqueIdentifier</code> of the <code>Role</code>.
		 *
		 * @return the <code>UniqueIdentifier</code> of the <code>Role</code>.
		 */
		private UniqueIdentifier getRoleIdentifier() {
			return roleIdentifier;
		}

		/**
		 * Returns the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 *
		 * @return the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 */
		private UniqueIdentifier getGoalIdentifier() {
			return goalIdentifier;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof AssignmentIdentifier) {
				final AssignmentIdentifier assignmentIdentifier = (AssignmentIdentifier) object;
				return getAgentIdentifier().equals(assignmentIdentifier.getAgentIdentifier())
						&& getRoleIdentifier().equals(assignmentIdentifier.getRoleIdentifier())
								&& getGoalIdentifier().equals(assignmentIdentifier.getGoalIdentifier());
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hashCode == null) {
				hashCode = getAgentIdentifier().hashCode() << 22 | getRoleIdentifier().hashCode() << 11 | getGoalIdentifier().hashCode();
			}
			return hashCode;
		}

		@Override
		public String toString() {
			if (toString == null) {
				toString = String.format(STRING_FORMAT, getAgentIdentifier(), getRoleIdentifier(), getGoalIdentifier());
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
	 * The <code>UniqueIdentifier</code> identifying this <code>Assignment</code> relation.
	 */
	private final UniqueIdentifier identifier;

	/**
	 * The <code>Agent</code> of this <code>Assignment</code> relation.
	 */
	private final Agent<?> agent;

	/**
	 * The <code>Role</code> of this <code>Assignment</code> relation.
	 */
	private final Role role;

	/**
	 * The <code>InstanceGoal</code> of this <code>Assignment</code> relation.
	 */
	private final InstanceGoal<?> instanceGoal;

	/**
	 * Constructs a new instance of <code>Assignment</code>.
	 *
	 * @param agent
	 *            the <code>Agent</code> of this <code>Assignment</code> relation.
	 * @param role
	 *            the <code>Role</code> of this <code>Assignment</code> relation.
	 * @param instanceGoal
	 *            the <code>InstanceGoal</code> of this <code>Assignment</code> relation.
	 */
	public Assignment(final Agent<?> agent, final Role role, final InstanceGoal<?> instanceGoal) {
		if (agent == null || role == null || instanceGoal == null) {
			throw new IllegalArgumentException(String.format("Parameters (agent: %s, role: %s, instance goal: %s) cannot be null", agent, role, instanceGoal));
		}
		this.agent = agent;
		this.role = role;
		this.instanceGoal = instanceGoal;
		identifier = new AssignmentIdentifier(agent.getIdentifier(), role.getIdentifier(), instanceGoal.getIdentifier());
	}

	/**
	 * Returns the <code>UniqueIdentifier</code> identifying this <code>Assignment</code> relation.
	 *
	 * @return the <code>UniqueIdentifier</code> identifying this <code>Assignment</code> relation.
	 */
	public final UniqueIdentifier getIdentifier() {
		return identifier;
	}

	/**
	 * Returns the <code>Agent</code> of this <code>Assignment</code> relation.
	 *
	 * @return the <code>Agent</code> of this <code>Assignment</code> relation.
	 */
	public final Agent<?> getAgent() {
		return agent;
	}

	/**
	 * Returns the <code>Role</code> of this <code>Assignment</code> relation.
	 *
	 * @return the <code>Role</code> of this <code>Assignment</code> relation.
	 */
	public final Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>InstanceGoal</code> of this <code>Assignment</code> relation.
	 *
	 * @return the <code>InstanceGoal</code> of this <code>Assignment</code> relation.
	 */
	public final InstanceGoal<?> getInstanceGoal() {
		return instanceGoal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Assignment) {
			final Assignment assignment = (Assignment) object;
			return getIdentifier().equals(assignment.getIdentifier());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public String toString() {
		return getInstanceGoal().getParameter() == null ? String.format(STRING_FORMAT_NO_PARAMETERS, getIdentifier()) : String.format(STRING_FORMAT_PARAMETERS,
				getIdentifier(), getInstanceGoal().getParameter());
	}

}
