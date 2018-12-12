package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.model.event.EventType

/**
 * The [AgentEventFactory] interface defines the API for constructing [AgentEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AgentEventFactory {
    /**
     * Constructs an [AgentEvent].
     *
     * @param category the [EventType].
     * @param agent    the [Agent].
     * @return a [AgentEvent].
     */
    fun build(category: EventType, agent: Agent): AgentEvent
}
