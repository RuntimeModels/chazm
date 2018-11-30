package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AttributeId

/**
 * The [AttributeManager] interface defines APIs that can be used for managing a set of [Attribute]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface AttributeManager : Map<AttributeId, Attribute> {
    /**
     * Adds an [Attribute] to this [AttributeManager].
     *
     * @param attribute the [Attribute] to add.
     */
    fun add(attribute: Attribute)

    /**
     * Removes an [Attribute] from this [AttributeManager].
     *
     * @param id the [AttributeId] that represents the [Attribute] to remove.
     * @return the [Attribute] that was removed.
     */
    fun remove(id: AttributeId): Attribute
}
