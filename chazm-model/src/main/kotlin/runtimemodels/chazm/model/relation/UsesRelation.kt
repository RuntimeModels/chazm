package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Uses
import javax.inject.Inject

internal data class UsesRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val pmf: Pmf
) : Uses
