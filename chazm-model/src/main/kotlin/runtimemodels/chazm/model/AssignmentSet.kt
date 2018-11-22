package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Assignment

@Suppress("unused")
class AssignmentSet {
    private val assignments: MutableList<Assignment> = ArrayList()
    private val assignmentsByAgent: MutableMap<Agent, MutableList<Assignment>> = HashMap()
    private val assignmentsByRole: MutableMap<Role, MutableList<Assignment>> = HashMap()
    private val assignmentsByGoal: MutableMap<InstanceGoal, MutableList<Assignment>> = HashMap()

    @Synchronized
    fun addAssignment(assignment: Assignment) {
        assignments.add(assignment)
        assignmentsByAgent.computeIfAbsent(assignment.agent) { ArrayList() }.add(assignment)
        assignmentsByRole.computeIfAbsent(assignment.role) { ArrayList() }.add(assignment)
        assignmentsByGoal.computeIfAbsent(assignment.goal) { ArrayList() }.add(assignment)
    }

    @Synchronized
    fun addAssignments(assignments: Collection<Assignment>) = assignments.forEach(::addAssignment)


    @Synchronized
    fun removeAssignment(assignment: Assignment) {
        assignments.remove(assignment)
        assignmentsByAgent[assignment.agent]?.remove(assignment)
        assignmentsByRole[assignment.role]?.remove(assignment)
        assignmentsByGoal[assignment.goal]?.remove(assignment)
    }

    @Synchronized
    fun removeAssignments(assignments: Collection<Assignment>) = assignments.forEach(::removeAssignment)

    @Synchronized
    fun getAssignments(): Set<Assignment> = assignments.toSet()

    @Synchronized
    fun getAssignmentsByAgent(agent: Agent): Set<Assignment> = assignmentsByAgent.getOrDefault(agent, mutableListOf()).toSet()

    @Synchronized
    fun getAssignmentsByRole(role: Role): Set<Assignment> = assignmentsByRole.getOrDefault(role, mutableListOf()).toSet()

    @Synchronized
    fun getAssignmentsByGoal(goal: InstanceGoal): Set<Assignment> = assignmentsByGoal.getOrDefault(goal, mutableListOf()).toSet()

}
