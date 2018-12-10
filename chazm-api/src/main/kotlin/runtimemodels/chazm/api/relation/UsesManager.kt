package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.RoleId

/**
 * The [UsesManager] interface defines the APIs for managing a set of [Uses] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface UsesManager {
    /**
     * Adds a [Uses] relation between a [Role] and a [Pmf] to this [UsesManager].
     *
     * @param uses the [Uses] to add.
     */
    fun add(uses: Uses)

    /**
     * Returns the [Uses] relation between a [Role] and a [Pmf] from this [PossessesManager].
     *
     * @param roleId           the [RoleId] that represents the [Role].
     * @param pmfId the [PmfId] that represents the [Pmf].
     * @return the [Uses] relation if it exists, `null` otherwise.
     */
    operator fun get(roleId: RoleId, pmfId: PmfId): Uses?

    /**
     * Returns a [Map] of [Pmf]s that is required by a [Role] in this [UsesManager].
     *
     * @param id the [RoleId] that represents the [Role].
     * @return the [Map] of [Pmf]s.
     */
    operator fun get(id: RoleId): Map<PmfId, Uses>

    /**
     * Returns a [Map] of [Role]s that requires a [Pmf] in this [UsesManager].
     *
     * @param id the [PmfId] that represents the [Pmf].
     * @return a [Map] of [Role]s.
     */
    operator fun get(id: PmfId): Map<RoleId, Uses>

    /**
     * Removes a [Uses] relation between a [Role] and a [Pmf] from this [UsesManager].
     *
     * @param roleId the [RoleId] that represents the [Role].
     * @param pmfId  the [PmfId] that represents the [Pmf].
     * @return the [Uses] that was removed, `null` otherwise.
     */
    fun remove(roleId: RoleId, pmfId: PmfId): Uses?

    /**
     * Removes all [Uses] relations associated to a [Role] from this [UsesManager].
     *
     * @param id the [RoleId] that represents the [Role].
     */
    fun remove(id: RoleId)

    /**
     * Removes all [Uses] relations associated to a [Pmf] from this [UsesManager].
     *
     * @param id the [PmfId] that represents the [Pmf].
     */
    fun remove(id: PmfId)
}
