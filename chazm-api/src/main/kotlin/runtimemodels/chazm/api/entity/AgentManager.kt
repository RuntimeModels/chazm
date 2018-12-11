package runtimemodels.chazm.api.entity

/**
 * The [AgentManager] interface defines APIs that can be used for managing a [Set] of [Agent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface AgentManager {
    /**
     * Adds an [Agent] to this [AgentManager].

     * @param agent the [Agent]s to add.
     */
    fun add(agent: Agent)

    /**
     * Removes an [Agent] from this [AgentManager].
     *
     * @param id the [AgentId] that represents the [Agent] to remove.
     * @return the [Agent] that was removed, `null` otherwise.
     */
    fun remove(id: AgentId): Agent?
}
