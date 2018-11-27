package runtimemodels.chazm.api.function

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Assignment

/**
 * The [Goodness] interface defines the API for computing a score (`0.0` <= score <= `1.0`) of how effective an
 * [Agent] is at playing a [Role] to achieve an [InstanceGoal] in an [Organization].
 *
 * @author Christopher Zhong
 * @since 6.0
 */
@FunctionalInterface
interface Goodness {
    /**
     * Returns a score (`0.0` <= score <=`1.0`) of how effective an [Agent] is at playing a [Role] to achieve
     * an [InstanceGoal] in an [Organization].
     *
     * @param organization an [Organization] in which to compute a score.
     * @param agent        the [Agent] that is playing the given [Role].
     * @param role         the [Role] that achieves the given [InstanceGoal].
     * @param goal         the [InstanceGoal] to compute a score.
     * @param assignments  a set of [Assignment]s that may affect the score.
     * @return a score (`0.0` <= score <= `1.0`).
     */
    fun compute(
        organization: Organization,
        agent: Agent,
        role: Role,
        goal: InstanceGoal,
        assignments: Collection<Assignment>
    ): Double
}
