package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.SpecificationGoalId
import javax.inject.Inject

internal data class SpecificationGoalEntity @Inject constructor(
    @Assisted override val id: SpecificationGoalId
) : SpecificationGoal
