package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.SpecificationGoalId
import javax.inject.Inject

internal data class DefaultSpecificationGoalId @Inject constructor(
    private val id: String
) : AbstractId<SpecificationGoal>(SpecificationGoal::class), SpecificationGoalId
