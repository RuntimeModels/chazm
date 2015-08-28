package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.chazm.relation.Assignment;
import io.github.runtimemodels.message.M;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

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
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	AssignmentEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Assignment assignment) {
		super(category);
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

	@Override
	public boolean equals(final Object object) {
		if (object instanceof AssignmentEvent) {
			final AssignmentEvent event = (AssignmentEvent) object;
			return super.equals(event) && getAgentId().equals(event.getAgentId()) && getRoleId().equals(event.getRoleId())
					&& getGoalId().equals(event.getGoalId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getCategory(), getAgentId(), getRoleId(), getGoalId());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_3_IDS.get(super.toString(), getAgentId(), getRoleId(), getGoalId());
		}
		return toString;
	}

}
