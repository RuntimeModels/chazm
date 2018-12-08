package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.relation.Has

/**
 * The [HasManager] interface defines the APIs for managing a set of [Has] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface HasManager {
    /**
     * Adds a [Has] relation between an [Agent] and an [Attribute] to this [HasManager].
     *
     * @param has the [Has] relation to add.
     */
    fun add(has: Has)

    /**
     * Returns the [Has] relation between an [Agent] and an [Attribute] from this [HasManager].
     *
     * @param agentId the [AgentId] that represents the [Agent].
     * @param attributeId the [AttributeId] that represents the [Attribute].
     * @return the [Has] relation if it exists, `null` otherwise.
     */
    operator fun get(agentId: AgentId, attributeId: AttributeId): Has?

    /**
     * Returns a [Map] of [Attribute]s that an [Agent] has in this [HasManager].
     *
     * @param id the [AgentId] that represents the [Agent].
     * @return a [Map] of [Attribute]s.
     */
    operator fun get(id: AgentId): Map<AttributeId, Has>

    /**
     * Returns a [Map] of [Agent]s that has an [Attribute] in this [HasManager].
     *
     * @param id the [AttributeId] that represents the [Attribute].
     * @return a [Map] of [Agent]s.
     */
    operator fun get(id: AttributeId): Map<AgentId, Has>

    /**
     * Removes a [Has] relation between a [Agent] and a [Attribute] from this [HasManager].
     *
     * @param agentId           the [AgentId] that represents the [Agent].
     * @param attributeId the [AttributeId] that represents the [Attribute].
     * @return the [Attribute] that was removed, `null` otherwise.
     */
    fun remove(agentId: AgentId, attributeId: AttributeId): Has?

    /**
     * Removes all [Has] relations associated to a [Agent] from this [HasManager].
     *
     * @param id the [AgentId] that represents the [Agent].
     */
    fun remove(id: AgentId)

    /**
     * Removes all [Has] relations associated to a [Attribute] from this [HasManager].
     *
     * @param id the [AttributeId] that represents the [Attribute].
     */
    fun remove(id: AttributeId)
}
