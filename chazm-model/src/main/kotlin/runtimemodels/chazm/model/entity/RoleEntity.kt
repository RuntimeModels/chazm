package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.RoleId
import javax.inject.Inject

internal data class RoleEntity @Inject constructor(
    @Assisted override val id: RoleId
) : Role
