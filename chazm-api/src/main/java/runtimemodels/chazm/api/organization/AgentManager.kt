package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.AgentId

/**
 * The [AgentManager] interface defines APIs that can be used for managing a [Set] of [Agent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface AgentManager : Map<AgentId, Agent> {
    /**
     * Adds an [Agent] to this [AgentManager].

     * @param agent the [Agent]s to add.
     */
    fun add(agent: Agent)

    /**
     * Removes an [Agent] from this [AgentManager].
     *
     * @param id the [AgentId] that represents the [Agent] to remove.
     */
    fun remove(id: AgentId): Agent
}
