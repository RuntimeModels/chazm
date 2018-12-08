package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Needs

/**
 * The [NeedsManager] interface defines the APIs for managing a set of [Needs] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface NeedsManager {
    /**
     * Adds a [Needs] relation between a [Role] and an [Attribute] to this [NeedsManager].
     *
     * @param needs the [Needs] relation to add.
     */
    fun add(needs: Needs)

    /**
     * Returns the [Needs] relation between an [Role] and an [Attribute] from this [HasManager].
     *
     * @param roleId           the [RoleId] that represents the [Role].
     * @param attributeId the [AttributeId] that represents the [Attribute].
     * @return the [Needs] relation if it exists, `null` otherwise.
     */
    operator fun get(roleId: RoleId, attributeId: AttributeId): Needs?

    /**
     * Returns a [Map] of [Attribute]s that are contained by a [Role] from this [NeedsManager].
     *
     * @param id the [RoleId] that represents the [Role].
     * @return a [Map] of [Attribute]s.
     */
    operator fun get(id: RoleId): Map<AttributeId, Needs>

    /**
     * Returns a [Map] of [Role]s that contains a [Attribute] from this [NeedsManager].
     *
     * @param id the [AttributeId] that represents the [Attribute].
     * @return a [Map] of [Role]s.
     */
    operator fun get(id: AttributeId): Map<RoleId, Needs>

    /**
     * Removes a [Needs] relation between a [Role] and an [Attribute] from this [NeedsManager].
     *
     * @param roleId      the [RoleId] that represents the [Role].
     * @param attributeId the [AttributeId] that represents the [Attribute].
     * @return the [Needs] relation that was removed, `null` otherwise.
     */
    fun remove(roleId: RoleId, attributeId: AttributeId): Needs?

    /**
     * Removes all [Needs] relations associated to a [Role] from this [NeedsManager].
     *
     * @param id the [RoleId] that represents the [Role].
     */
    fun remove(id: RoleId)

    /**
     * Removes all [Needs] relations associated to a [Attribute] from this [NeedsManager].
     *
     * @param id the [AttributeId] that represents the [Attribute].
     */
    fun remove(id: AttributeId)
}
