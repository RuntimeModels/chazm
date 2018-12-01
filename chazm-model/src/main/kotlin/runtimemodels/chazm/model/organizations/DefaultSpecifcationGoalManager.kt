package runtimemodels.chazm.model.organizations

import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.organization.SpecificationGoalManager
import runtimemodels.chazm.model.exceptions.SpecifcationGoalNotExistsException
import runtimemodels.chazm.model.exceptions.SpecificationGoalExistsException
import javax.inject.Inject

internal data class DefaultSpecifcationGoalManager @Inject constructor(
    private val map: MutableMap<SpecificationGoalId, SpecificationGoal>
) : SpecificationGoalManager, Map<SpecificationGoalId, SpecificationGoal> by map {
    override fun add(goal: SpecificationGoal) {
        if (map.containsKey(goal.id)) {
            throw SpecificationGoalExistsException(goal.id)
        }
        map[goal.id] = goal
    }

    override fun remove(id: SpecificationGoalId): SpecificationGoal {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw SpecifcationGoalNotExistsException(id)
    }
}
