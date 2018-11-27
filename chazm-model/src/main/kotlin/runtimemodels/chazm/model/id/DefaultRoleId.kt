package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.RoleId

internal data class DefaultRoleId(
    private val id: String
) : AbstractId<Role>(Role::class.java), RoleId
