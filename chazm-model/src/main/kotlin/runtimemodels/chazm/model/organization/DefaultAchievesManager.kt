package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.organization.AchievesManager
import runtimemodels.chazm.api.relation.Achieves
import javax.inject.Inject

internal data class DefaultAchievesManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<SpecificationGoalId, Achieves>>,
    private val byMap: MutableMap<SpecificationGoalId, MutableMap<RoleId, Achieves>>
) : AchievesManager {

    override fun add(achieves: Achieves) {
        if (map[achieves.role.id]?.containsKey(achieves.goal.id) == true) {
            return
        }
        map.computeIfAbsent(achieves.role.id) { mutableMapOf() }[achieves.goal.id] = achieves
        byMap.computeIfAbsent(achieves.goal.id) { mutableMapOf() }[achieves.role.id] = achieves
    }

    override operator fun get(roleId: RoleId, goalId: SpecificationGoalId): Achieves? = map[roleId]?.get(goalId)

    override operator fun get(id: RoleId): Map<SpecificationGoalId, Achieves> = map[id] ?: emptyMap()

    override operator fun get(id: SpecificationGoalId): Map<RoleId, Achieves> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, goalId: SpecificationGoalId): Achieves? {
        return map[roleId]?.remove(goalId)?.also {
            byMap[goalId]?.apply {
                remove(roleId)?.also { achieves ->
                    if (it === achieves) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$achieves' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$goalId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$goalId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: SpecificationGoalId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
