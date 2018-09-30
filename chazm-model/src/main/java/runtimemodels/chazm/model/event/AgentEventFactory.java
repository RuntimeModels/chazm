package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.entity.Agent;

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
     * @param category the {@linkplain EventType}.
     * @param agent    the {@linkplain Agent}.
     * @return a {@linkplain AgentEvent}.
     */
    AgentEvent build(EventType category, Agent agent);

}
