package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Agent;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain AgentEvent} class indicates that there is an update about an {@linkplain Agent} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class AgentEvent extends AbstractEvent {

	private static final long serialVersionUID = -1802353103746617813L;
	private final UniqueId<Agent> id;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	AgentEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Agent agent) {
		super(category);
		checkNotNull(agent, "agent");
		id = agent.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Agent}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Agent> getId() {
		return id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof AgentEvent) {
			final AgentEvent agentEvent = (AgentEvent) object;
			return super.equals(agentEvent) && getId().equals(agentEvent.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = super.hashCode() << 16 | getId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("AgentEvent(%s, %s)", getCategory(), getId());
		}
		return toString;
	}

}
