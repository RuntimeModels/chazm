package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.id.InstanceGoalId

internal data class DefaultInstanceGoalId(
    private val id: String
) : AbstractId<InstanceGoal>(InstanceGoal::class.java), InstanceGoalId
