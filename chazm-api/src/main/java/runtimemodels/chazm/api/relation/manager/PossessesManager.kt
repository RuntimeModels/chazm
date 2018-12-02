package runtimemodels.chazm.api.relation.manager

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Possesses

/**
 * The [PossessesManager] interface defines the APIs for managing [Possesses].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface PossessesManager {
    /**
     * Creates a possesses relation between an [Agent] and a [Capability] in this [PossessesManager].
     *
     * @param agentId      the [UniqueId] that represents the [Agent].
     * @param capabilityId the [UniqueId] that represents the [Capability].
     * @param score        the `double` score of the possesses relation.
     */
    fun addPossesses(agentId: AgentId, capabilityId: CapabilityId, score: Double)

    /**
     * Returns a set of [Capability]s that is possessed by an [Agent] in this [PossessesManager].
     *
     * @param id the [UniqueId] that represents the [Agent].
     * @return a set of [Capability]s.
     */
    fun getPossesses(id: AgentId): Set<Capability>

    /**
     * Returns a set of [Agent]s that possesses a [Capability] in this [PossessesManager].
     *
     * @param id the [UniqueId] that represents the [Capability].
     * @return a set of [Agent]s.
     */
    fun getPossessedBy(id: CapabilityId): Set<Agent>

    /**
     * Returns the `double` score of the possesses relation between an [Agent] and a [Capability] in this
     * [PossessesManager].
     *
     * @param agentId      the [UniqueId] that represents the [Agent].
     * @param capabilityId the [UniqueId] that represents the [Capability].
     * @return the `double` score.
     */
    fun getPossessesScore(agentId: AgentId, capabilityId: CapabilityId): Double

    /**
     * Sets a new `double` score for the possesses relation between an [Agent] and a [Capability] in this
     * [PossessesManager].
     *
     * @param agentId      the [UniqueId] that represents the [Agent].
     * @param capabilityId the [UniqueId] that represents the [Capability].
     * @param score        the new `double` score.
     */
    fun setPossessesScore(agentId: AgentId, capabilityId: CapabilityId, score: Double)

    /**
     * Removes a possesses relation between an [Agent] and a [Capability] from this [PossessesManager].
     *
     * @param agentId      the [UniqueId] that represents the [Agent].
     * @param capabilityId the [UniqueId] that represents the [Capability].
     */
    fun removePossesses(agentId: AgentId, capabilityId: CapabilityId)

    /**
     * Removes all possesses relations from this [PossessesManager].
     */
    fun removeAllPossesses()
}
