package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [RoleFactory] interface defines the APIs for constructing [Role]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface RoleFactory {

    /**
     * Constructs a [Role].
     *
     * @param id the [UniqueId] that represents the [Role].
     * @return a [Role].
     */
    fun buildRole(id: RoleId): Role

}
