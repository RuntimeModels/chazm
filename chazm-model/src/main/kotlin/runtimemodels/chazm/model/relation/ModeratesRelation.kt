package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.relation.Moderates
import javax.inject.Inject

internal data class ModeratesRelation @Inject constructor(
    @param:Assisted override val pmf: Pmf,
    @param:Assisted override val attribute: Attribute
) : Moderates
