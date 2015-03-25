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
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param agent
	 *            the {@linkplain Agent}.
	 *
	 * @return a {@linkplain AgentEvent}.
	 */
	AgentEvent build(EventCategory category, Agent agent);

}