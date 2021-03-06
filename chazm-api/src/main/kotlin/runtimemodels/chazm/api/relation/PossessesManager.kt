package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.AgentId
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.CapabilityId

/**
 * The [PossessesManager] interface defines the APIs for managing a set of [Possesses] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface PossessesManager {
    /**
     * Adds a [Possesses] relation between an [Agent] and a [Capability] to this [PossessesManager].
     *
     * @param possesses the [Possesses] relation to add.
     */
    fun add(possesses: Possesses)
    /**
     * Returns the [Possesses] relation between an [Agent] and an [Capability] from this [PossessesManager].
     *
     * @param agentId           the [AgentId] that represents the [Agent].
     * @param capabilityId the [CapabilityId] that represents the [Capability].
     * @return the [Possesses] relation if it exists, `null` otherwise.
     */
    operator fun get(agentId: AgentId, capabilityId: CapabilityId): Possesses?

    /**
     * Returns a [Map] of [Capability]s that an [Agent] possesses in this [PossessesManager].
     *
     * @param id the [AgentId] that represents the [Agent].
     * @return a [Map] of [Capability]s.
     */
    operator fun get(id: AgentId): Map<CapabilityId, Possesses>

    /**
     * Returns a [Map] of [Agent]s that possesses a [Capability] in this [PossessesManager].
     *
     * @param id the [CapabilityId] that represents the [Capability].
     * @return a [Map] of [Agent]s.
     */
    operator fun get(id: CapabilityId): Map<AgentId, Possesses>

    /**
     * Removes a [Possesses] relation between an [Agent] and a [Capability] from this [PossessesManager].
     *
     * @param agentId      the [AgentId] that represents the [Agent].
     * @param capabilityId the [CapabilityId] that represents the [Capability].
     * @return the [Possesses] relation that was removed, `null` otherwise.
     */
    fun remove(agentId: AgentId, capabilityId: CapabilityId): Possesses?

    /**
     * Removes all [Possesses] relations associated to an [Agent] from this [NeedsManager].
     *
     * @param id the [AgentId] that represents the [Agent].
     */
    fun remove(id: AgentId)

    /**
     * Removes all [Possesses] relations associated to a [Capability] from this [NeedsManager].
     *
     * @param id the [CapabilityId] that represents the [Capability].
     */
    fun remove(id: CapabilityId)
}
