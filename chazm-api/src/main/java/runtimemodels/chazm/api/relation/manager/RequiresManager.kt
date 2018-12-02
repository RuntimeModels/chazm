package runtimemodels.chazm.api.relation.manager

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Requires

/**
 * The [RequiresManager] interface defines the APIs for managing the [Requires].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface RequiresManager {
    /**
     * Creates a requires relation between a [Role] and a [Capability] in this [RequiresManager].
     *
     * @param roleId       the [UniqueId] that represents the [Role].
     * @param capabilityId the [UniqueId] that represents the [Capability].
     */
    fun addRequires(roleId: RoleId, capabilityId: CapabilityId)

    /**
     * Returns a set of [Capability]s that is required by a [Role] in this [RequiresManager].
     *
     * @param id the [UniqueId] that represents the [Role].
     * @return the set of [Capability]s.
     */
    fun getRequires(id: RoleId): Set<Capability>

    /**
     * Returns a set of [Role]s that requires a [Capability] in this [RequiresManager].
     *
     * @param id the [UniqueId] that represents the [Capability].
     * @return a set of [Role]s.
     */
    fun getRequiredBy(id: CapabilityId): Set<Role>

    /**
     * Removes a requires relation between a [Role] and a [Capability] from this [RequiresManager].
     *
     * @param roleId       the [UniqueId] that represents the [Role].
     * @param capabilityId the [UniqueId] that represents the [Capability].
     */
    fun removeRequires(roleId: RoleId, capabilityId: CapabilityId)

    /**
     * Removes all requires relations from this [RequiresManager].
     */
    fun removeAllRequires()
}
