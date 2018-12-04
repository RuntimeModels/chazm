package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Assignment] interface defines an assignment relation, which means that an [Agent] is
 * assigned to play a [Role] to achieve an [InstanceGoal], of an [Organization].
 *
 * @author Christopher Zhong
 * @see Agent
 * @see Role
 * @see InstanceGoal
 * @since 7.0.0
 */
interface Assignment {
    /**
     * The [Agent] of this [Assignment].
     */
    val agent: Agent

    /**
     * The [Role] of this [Assignment].
     */
    val role: Role

    /**
     * The [InstanceGoal] of this [Assignment].
     */
    val goal: InstanceGoal
}
