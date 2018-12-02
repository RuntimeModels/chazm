package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Contains

/**
 * The [ContainsManager] interface defines the APIs for managing a set of [Contains] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface ContainsManager : Map<RoleId, Map<CharacteristicId, Contains>> {
    /**
     * Adds a [Contains] relation between a [Role] and a [Characteristic] to this [ContainsManager].
     *
     * @param contains           the [Contains] relation to add.
     */
    fun add(contains: Contains)

    /**
     * Returns a [Map] of the [Characteristic]s that are contained by a [Role].
     *
     * @param key the [RoleId] that represents the [Role].
     */
    override operator fun get(key: RoleId): Map<CharacteristicId, Contains>

    /**
     * Returns a [Map] of the [Role]s that contains a [Characteristic].
     *
     * @param id the [CharacteristicId] that represents the [Characteristic].
     */
    operator fun get(id: CharacteristicId): Map<RoleId, Contains>

    /**
     * Removes a [Contains] relation between a [Role] and a [Characteristic] from this [ContainsManager].
     *
     * @param roleId           the [RoleId] that represents the [Role].
     * @param characteristicId the [CharacteristicId] that represents the [Characteristic].
     * @return the [Characteristic] that was removed.
     */
    fun remove(roleId: RoleId, characteristicId: CharacteristicId): Contains

    /**
     * Removes all [Contains] relations associated to a [Role] from this [ContainsManager].
     *
     * @param id the [RoleId] that represents the [Role].
     */
    fun remove(id: RoleId)

    /**
     * Removes all [Contains] relations associated to a [Characteristic] from this [ContainsManager].
     *
     * @param id the [CharacteristicId] that represents the [Characteristic].
     */
    fun remove(id: CharacteristicId)
}
