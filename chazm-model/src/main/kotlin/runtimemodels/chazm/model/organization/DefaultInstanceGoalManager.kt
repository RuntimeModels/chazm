package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.organization.InstanceGoalManager
import runtimemodels.chazm.model.exceptions.InstanceGoalExistsException
import runtimemodels.chazm.model.exceptions.InstanceGoalNotExistsException
import javax.inject.Inject

internal data class DefaultInstanceGoalManager @Inject constructor(
    private val map: MutableMap<InstanceGoalId, InstanceGoal>,
    private val byMap: MutableMap<SpecificationGoalId, MutableMap<InstanceGoalId, InstanceGoal>>
) : InstanceGoalManager, Map<InstanceGoalId, InstanceGoal> by map {
    override fun add(goal: InstanceGoal) {
        if (map.containsKey(goal.id)) {
            throw InstanceGoalExistsException(goal.id)
        }
        map[goal.id] = goal
        byMap.computeIfAbsent(goal.goal.id) { mutableMapOf() }[goal.id] = goal
    }

    override fun remove(id: InstanceGoalId): InstanceGoal {
        if (map.containsKey(id)) {
            val goal = map.remove(id)!!
            if (byMap.containsKey(goal.goal.id)) {
                val m = byMap[goal.goal.id]!!
                if (m.containsKey(id)) {
                    val g = m.remove(id)!!
                    if (goal === g) {
                        return goal
                    } else {
                        throw IllegalStateException("the goal '$g' obtained from 'byMap'[${g.goal.id}][$id] is different from '$goal'")
                    }
                } else {
                    throw IllegalStateException("the key '$id' is missing from the map return by 'byMap[${goal.goal.id}]'")
                }
            } else {
                throw IllegalStateException("the key '${goal.goal.id}' is missing from map 'byMap'")
            }
        }
        throw InstanceGoalNotExistsException(id)
    }

    override fun remove(id: SpecificationGoalId) {
        val mBy = byMap.remove(id)
        mBy?.forEach { map.remove(it.key) }
    }
}
