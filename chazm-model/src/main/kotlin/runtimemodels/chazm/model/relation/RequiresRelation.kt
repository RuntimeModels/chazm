package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Requires
import javax.inject.Inject

internal data class RequiresRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val capability: Capability
) : Requires
