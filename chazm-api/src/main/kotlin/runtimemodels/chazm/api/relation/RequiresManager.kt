package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.CapabilityId
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.RoleId

/**
 * The [RequiresManager] interface defines the APIs for managing a set of [Requires] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface RequiresManager {
    /**
     * Adds a [Requires] relation between a [Role] and a [Capability] to this [RequiresManager].
     *
     * @param requires       the [Requires] to add.
     */
    fun add(requires: Requires)

    /**
     * Returns the [Requires] relation between a [Role] and a [Capability] from this [PossessesManager].
     *
     * @param roleId           the [RoleId] that represents the [Role].
     * @param capabilityId the [CapabilityId] that represents the [Capability].
     * @return the [Requires] relation if it exists, `null` otherwise.
     */
    operator fun get(roleId: RoleId, capabilityId: CapabilityId): Requires?

    /**
     * Returns a [Map] of [Capability]s that is required by a [Role] in this [RequiresManager].
     *
     * @param id the [RoleId] that represents the [Role].
     * @return the [Map] of [Capability]s.
     */
    operator fun get(id: RoleId): Map<CapabilityId, Requires>

    /**
     * Returns a [Map] of [Role]s that requires a [Capability] in this [RequiresManager].
     *
     * @param id the [CapabilityId] that represents the [Capability].
     * @return a [Map] of [Role]s.
     */
    operator fun get(id: CapabilityId): Map<RoleId, Requires>

    /**
     * Removes a [Requires] relation between a [Role] and a [Capability] from this [RequiresManager].
     *
     * @param roleId       the [RoleId] that represents the [Role].
     * @param capabilityId the [CapabilityId] that represents the [Capability].
     * @return the [Requires] relation that was removed, `null` otherwise.
     */
    fun remove(roleId: RoleId, capabilityId: CapabilityId): Requires?

    /**
     * Removes all [Requires] relations associated to a [Role] from this [RequiresManager].
     *
     * @param id the [RoleId] that represents the [Role].
     */
    fun remove(id: RoleId)

    /**
     * Removes all [Requires] relations associated to a [Capability] from this [RequiresManager].
     *
     * @param id the [CapabilityId] that represents the [Capability].
     */
    fun remove(id: CapabilityId)
}
