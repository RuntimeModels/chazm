package runtimemodels.chazm.api.entity

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
     * @return the [Attribute] that was removed, `null` otherwise.
     */
    fun remove(id: AttributeId): Attribute?
}
