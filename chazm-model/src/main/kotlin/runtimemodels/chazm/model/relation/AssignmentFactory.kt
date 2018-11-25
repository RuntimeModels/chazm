package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Assignment

/**
 * The [AssignmentFactory] interface defines the API for constructing [Assignment]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AssignmentFactory {

    /**
     * Constructs an [Assignment].
     *
     * @param agent the [Agent] of the [Assignment].
     * @param role  the [Role] of the [Assignment].
     * @param goal  the [InstanceGoal] of the [Assignment].
     * @return an [Assignment].
     */
    fun buildAssignment(agent: Agent, role: Role, goal: InstanceGoal): Assignment

}
