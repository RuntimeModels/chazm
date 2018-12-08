package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Needs
import javax.inject.Inject

internal data class NeedsRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val attribute: Attribute
) : Needs
