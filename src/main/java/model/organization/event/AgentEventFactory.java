package model.organization.event;

import model.organization.entity.Agent;

/**
 * The {@linkplain AgentEventFactory} interface defines the API for constructing {@linkplain AgentEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AgentEventFactory {

	/**
	 * Constructs an {@linkplain AgentEvent}.
	 *
	 * @param agent
	 *            the {@linkplain Agent}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain AgentEvent}.
	 */
	AgentEvent build(Agent agent, EventCategory category);

}