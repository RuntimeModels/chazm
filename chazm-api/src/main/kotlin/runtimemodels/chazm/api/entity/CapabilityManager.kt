package runtimemodels.chazm.api.entity

/**
 * The [CapabilityManager] interface defines APIs that can be used for managing a set [Capability]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface CapabilityManager : Map<CapabilityId, Capability> {
    /**
     * Adds a [Capability] to this [CapabilityManager].
     *
     * @param capability the [Capability] to add.
     */
    fun add(capability: Capability)

    /**
     * Removes a [Capability] from this [CapabilityManager].
     *
     * @param id the [CapabilityId] that represents the [Capability] to remove.
     * @return the [Capability] that was removed.
     */
    fun remove(id: CapabilityId): Capability
}
