package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.SpecificationGoalId

internal data class DefaultSpecificationGoalId(
    private val id: String
) : AbstractId<SpecificationGoal>(SpecificationGoal::class.java), SpecificationGoalId
