package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.organization.AchievesManager
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.model.exceptions.AchievesExistsException
import runtimemodels.chazm.model.exceptions.AchievesNotExistsException
import javax.inject.Inject

internal data class DefaultAchievesManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<SpecificationGoalId, Achieves>>,
    private val byMap: MutableMap<SpecificationGoalId, MutableMap<RoleId, Achieves>>
) : AchievesManager, Map<RoleId, Map<SpecificationGoalId, Achieves>> by map {

    override fun add(achieves: Achieves) {
        if (map.containsKey(achieves.role.id) && map[achieves.role.id]!!.containsKey(achieves.goal.id)) {
            throw AchievesExistsException(achieves.role.id, achieves.goal.id)
        }
        map.computeIfAbsent(achieves.role.id) { mutableMapOf() }[achieves.goal.id] = achieves
        byMap.computeIfAbsent(achieves.goal.id) { mutableMapOf() }[achieves.role.id] = achieves
    }

    override operator fun get(key: RoleId): Map<SpecificationGoalId, Achieves> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: SpecificationGoalId): Map<RoleId, Achieves> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(roleId: RoleId, goalId: SpecificationGoalId): Achieves {
        if (map.containsKey(roleId) && map[roleId]!!.containsKey(goalId)) {
            val achieves = map[roleId]!!.remove(goalId)!!
            if (byMap.containsKey(goalId)) {
                val m = byMap[goalId]!!
                if (m.containsKey(roleId)) {
                    val a = m.remove(roleId)!!
                    if (achieves === a) {
                        return achieves
                    }
                    throw IllegalStateException("the '$achieves' and '$a' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$goalId][$roleId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$goalId]' is missing!")
        }
        throw AchievesNotExistsException(roleId, goalId)
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
