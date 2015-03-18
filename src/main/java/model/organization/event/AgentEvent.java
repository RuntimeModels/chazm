package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Agent;
import model.organization.id.UniqueId;

/**
 * The {@linkplain AgentEvent} class indicates that there is an update about an {@linkplain Agent} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class AgentEvent extends AbstractEvent {

	private static final long serialVersionUID = -1802353103746617813L;
	private final UniqueId<Agent> id;

	/**
	 * Constructs a new instance of {@linkplain AgentEvent}.
	 *
	 * @param agent
	 *            the {@linkplain Agent}.
	 * @param category
	 *            the category of the update.
	 */
	public AgentEvent(final Agent agent, final UpdateCategory category) {
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

}
