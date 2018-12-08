package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.relation.Achieves
import javax.inject.Inject

internal data class AchievesRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val goal: SpecificationGoal
) : Achieves
