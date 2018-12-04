package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.id.InstanceGoalId
import javax.inject.Inject

internal data class DefaultInstanceGoalId @Inject constructor(
    private val id: String
) : AbstractId<InstanceGoal>(InstanceGoal::class), InstanceGoalId
