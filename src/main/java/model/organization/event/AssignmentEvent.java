package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Agent;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Assignment;

/**
 * The {@linkplain AssignmentEvent} class indicates that there is an update about an {@linkplain Assignment}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class AssignmentEvent extends AbstractEvent {

	private static final long serialVersionUID = -606847454040505425L;
	private final UniqueId<Agent> agentId;
	private final UniqueId<Role> roleId;
	private final UniqueId<InstanceGoal> goalId;

	/**
	 * Constructs a new instance of {@linkplain AssignmentEvent}.
	 *
	 * @param assignment
	 *            the {@linkplain Assignment}.
	 * @param category
	 *            the category of the update.
	 */
	public AssignmentEvent(final Assignment assignment, final UpdateCategory category) {
		super(category);
		checkNotNull(assignment, "assignment");
		agentId = assignment.getAgent().getId();
		roleId = assignment.getRole().getId();
		goalId = assignment.getGoal().getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents an {@linkplain Agent}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Agent> getAgentId() {
		return agentId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents an {@linkplain Role}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Role> getRoleId() {
		return roleId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents an {@linkplain InstanceGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<InstanceGoal> getGoalId() {
		return goalId;
	}

}
