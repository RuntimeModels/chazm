package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.RoleId
import javax.inject.Inject

internal data class DefaultRoleId @Inject constructor(
    private val id: String
) : AbstractId<Role>(Role::class), RoleId
