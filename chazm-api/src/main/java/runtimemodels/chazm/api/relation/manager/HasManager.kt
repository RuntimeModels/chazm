package runtimemodels.chazm.api.relation.manager

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Has

/**
 * The [HasManager] interface defines the APIs for managing [Has].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface HasManager {
    /**
     * Creates a has relation between an [Agent] and an [Attribute] in this [HasManager].
     *
     * @param agentId     the [UniqueId] that represents the [Agent].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     * @param value       the `double` value.
     */
    fun addHas(agentId: AgentId, attributeId: AttributeId, value: Double)

    /**
     * Returns a set of [Attribute]s that is had by an [Agent] in this [HasManager].
     *
     * @param id the [UniqueId] that represents the [Attribute].
     * @return a set of [Attribute]s.
     */
    fun getHas(id: AgentId): Set<Attribute>

    /**
     * Returns a set of [Agent]s that has an [Attribute] in this [HasManager].
     *
     * @param id the [UniqueId] that represents the [Agent].
     * @return a set of [Agent]s.
     */
    fun getHadBy(id: AttributeId): Set<Agent>

    /**
     * Returns the `double` value of the has relation between an [Agent] and an [Attribute] in this [HasManager].
     *
     * @param agentId     the [UniqueId] that represents the [Agent].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     * @return the `double` value if the has relation exists, `null` otherwise.
     */
    fun getHasValue(agentId: AgentId, attributeId: AttributeId): Double?

    /**
     * Sets a new `double` value for the needs relation between an [Agent] and an [Attribute] in this [HasManager].
     *
     * @param agentId     the [UniqueId] that represents the [Agent].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     * @param value       the new `double` value.
     */
    fun setHasValue(agentId: AgentId, attributeId: AttributeId, value: Double)

    /**
     * Removes a has relation between an [Agent] and an [Attribute] from this [HasManager].
     *
     * @param agentId     the [UniqueId] that represents the [Agent].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     */
    fun removeHas(agentId: AgentId, attributeId: AttributeId)

    /**
     * Removes all has relations from this [HasManager].
     */
    fun removeAllHas()
}
