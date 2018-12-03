package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.AssignmentManager
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.model.exceptions.AssignmentExistsException
import runtimemodels.chazm.model.exceptions.AssignmentNotExistsException
import javax.inject.Inject

internal data class DefaultAssignmentManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<RoleId, MutableMap<InstanceGoalId, Assignment>>>,
    private val byRoleMap: MutableMap<RoleId, MutableMap<AgentId, MutableMap<InstanceGoalId, Assignment>>>,
    private val byGoalMap: MutableMap<InstanceGoalId, MutableMap<AgentId, MutableMap<RoleId, Assignment>>>
) : AssignmentManager, Map<AgentId, Map<RoleId, Map<InstanceGoalId, Assignment>>> by map {

    override fun add(assignment: Assignment) {
        if (map[assignment.agent.id]?.get(assignment.role.id)?.containsKey(assignment.goal.id) == true) {
            throw AssignmentExistsException(assignment.agent.id, assignment.role.id, assignment.goal.id)
        }
        map.computeIfAbsent(assignment.agent.id) { mutableMapOf() }.computeIfAbsent(assignment.role.id) { mutableMapOf() }[assignment.goal.id] = assignment
        byRoleMap.computeIfAbsent(assignment.role.id) { mutableMapOf() }.computeIfAbsent(assignment.agent.id) { mutableMapOf() }[assignment.goal.id] = assignment
        byGoalMap.computeIfAbsent(assignment.goal.id) { mutableMapOf() }.computeIfAbsent(assignment.agent.id) { mutableMapOf() }[assignment.role.id] = assignment
    }

    override operator fun get(agentId: AgentId, roleId: RoleId, goalId: InstanceGoalId) = map[agentId]?.get(roleId)?.get(goalId)

    override operator fun get(key: AgentId) = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: RoleId) = byRoleMap.getOrDefault(id, mutableMapOf())

    override operator fun get(id: InstanceGoalId) = byGoalMap.getOrDefault(id, mutableMapOf())

    override fun remove(agentId: AgentId, roleId: RoleId, goalId: InstanceGoalId): Assignment {
        if (map[agentId]?.get(roleId)?.containsKey(goalId) == true) {
            val assignment = map[agentId]!![roleId]!!.remove(goalId)!!
            if (byRoleMap.containsKey(roleId)) {
                val m = byRoleMap[roleId]!!
                if (m.containsKey(agentId)) {
                    val c = m.remove(agentId)!!
                    if (assignment === c) {
                        return assignment
                    }
                    throw IllegalStateException("the '$assignment' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$roleId][$agentId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$roleId]' is missing!")
        }
        throw AssignmentNotExistsException(agentId, roleId, goalId)
    }

    override fun remove(id: AgentId) {
        map.remove(id)
        byRoleMap.forEach { it.value.remove(id) }
        byGoalMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: RoleId) {
        map.forEach { it.value.remove(id) }
        byRoleMap.remove(id)
        byGoalMap.forEach { it.value.forEach { it.value.remove(id) } }
    }

    override fun remove(id: InstanceGoalId) {
        map.forEach { it.value.forEach { it.value.remove(id) } }
        byRoleMap.forEach { it.value.forEach { it.value.remove(id) } }
        byGoalMap.remove(id)
    }
}
