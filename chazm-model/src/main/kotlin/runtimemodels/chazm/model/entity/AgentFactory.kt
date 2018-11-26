package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [AgentFactory] interface defines the APIs for constructing [Agent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AgentFactory {
    /**
     * Constructs an [Agent].
     *
     * @param id          the [UniqueId] that represents this [Agent].
     * @param contactInfo a [Map] with contact information for this [Agent].
     * @return an [Agent].
     */
    fun buildAgent(id: UniqueId<Agent>, contactInfo: Map<Any, Any>): Agent
}
