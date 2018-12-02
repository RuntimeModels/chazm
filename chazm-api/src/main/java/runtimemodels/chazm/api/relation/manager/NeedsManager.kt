package runtimemodels.chazm.api.relation.manager

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Needs

/**
 * The [NeedsManager] interface defines the API for managing [Needs].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface NeedsManager {
    /**
     * Creates a needs relation between a [Role] and an [Attribute] in this [NeedsManager].
     *
     * @param roleId      the [UniqueId] that represents the [Role].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     */
    fun addNeeds(roleId: RoleId, attributeId: AttributeId)

    /**
     * Returns a set of [Attribute]s that is needed by a [Role] in this [NeedsManager].
     *
     * @param id the [UniqueId] that represents the [Role].
     * @return a set of [Attribute]s.
     */
    fun getNeeds(id: RoleId): Set<Attribute>

    /**
     * Returns a set of [Role]s that needs an [Attribute] in this [NeedsManager].
     *
     * @param id the [UniqueId] that represents the [Attribute].
     * @return a set of [Role]s.
     */
    fun getNeededBy(id: AttributeId): Set<Role>

    /**
     * Removes a needs relation between a [Role] and an [Attribute] from this [NeedsManager].
     *
     * @param roleId      the [UniqueId] that represents the [Role].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     */
    fun removeNeeds(roleId: RoleId, attributeId: AttributeId)

    /**
     * Removes all needs relations from this [NeedsManager].
     */
    fun removeAllNeeds()

}
