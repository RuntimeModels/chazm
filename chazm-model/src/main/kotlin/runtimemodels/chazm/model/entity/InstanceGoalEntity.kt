package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.InstanceGoalId
import javax.inject.Inject

internal data class InstanceGoalEntity @Inject constructor(
    @Assisted override val id: InstanceGoalId,
    @param:Assisted override val goal: SpecificationGoal
) : InstanceGoal {
    internal data class DefaultGoalParameter(
        private val map: MutableMap<Any, Any> = mutableMapOf()
    ) : InstanceGoal.GoalParameter, Map<Any, Any> by map {
        override fun add(key: Any, value: Any) {
            map[key] = value
        }

        override fun remove(key: Any) {
            map.remove(key)
        }
    }

    override val parameters: InstanceGoal.GoalParameter = DefaultGoalParameter()
}
