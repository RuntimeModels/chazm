package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.RoleManager
import runtimemodels.chazm.model.exceptions.EntityExistsException
import runtimemodels.chazm.model.exceptions.EntityNotExistsException
import javax.inject.Inject

internal data class DefaultRoleManager @Inject constructor(
    private val map: MutableMap<RoleId, Role>
) : RoleManager, Map<RoleId, Role> by map {
    override fun add(role: Role) {
        if (map.containsKey(role.id)) {
            throw EntityExistsException(role.id)
        }
        map[role.id] = role
    }

    override fun remove(id: RoleId): Role {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}
