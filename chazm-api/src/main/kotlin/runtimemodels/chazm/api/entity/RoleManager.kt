package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.RoleId

/**
 * The [RoleManager] interface defines APIs that can be used for managing a set [Role]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface RoleManager : Map<RoleId, Role> {
    /**
     * Adds a [Role] to this [RoleManager].
     *
     * @param role the [Role] to add.
     */
    fun add(role: Role)

    /**
     * Removes a [Role] from this [RoleManager].
     *
     * @param id the [RoleId] that represents the [Role] to remove.
     * @return the [Role] that was removed.
     */
    fun remove(id: RoleId): Role
}
