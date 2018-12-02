package runtimemodels.chazm.api.relation.manager

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [UsesManager] interface defines the APIs for managing [Pmf]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface UsesManager {
    /**
     * Creates a uses relation between a [Role] and a [Pmf] in this [UsesManager].
     *
     * @param roleId the [UniqueId] that represents the [Role].
     * @param pmfId  the [UniqueId] that represents the [Pmf].
     */
    fun addUses(roleId: RoleId, pmfId: PmfId)

    /**
     * Returns a set of [Pmf]s that is used by a [Role] in this [UsesManager].
     *
     * @param id the [UniqueId] that represents the [Role].
     * @return a set of [Pmf]s.
     */
    fun getUses(id: RoleId): Set<Pmf>

    /**
     * Returns a set of [Role]s that uses a [Pmf] in this [UsesManager].
     *
     * @param id the [UniqueId] that represents the [Pmf].
     * @return a set of [Role]s.
     */
    fun getUsedBy(id: PmfId): Set<Role>

    /**
     * Removes a uses relation between a [Role] and a [Pmf] from this [UsesManager].
     *
     * @param roleId the [UniqueId] that represents the [Role].
     * @param pmfId  the [UniqueId] that represents the [Pmf].
     */
    fun removeUses(roleId: RoleId, pmfId: PmfId)

    /**
     * Removes all uses relations from this [UsesManager].
     */
    fun removeAllUses()
}
