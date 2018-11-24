package runtimemodels.chazm.model.function

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.relation.Assignment
import javax.inject.Singleton
import javax.validation.constraints.NotNull

@Singleton
internal open class DefaultGoodness : Goodness {

    override fun compute(@NotNull organization: Organization, @NotNull agent: Agent, @NotNull role: Role, @NotNull goal: InstanceGoal, @NotNull assignments: Collection<Assignment>): Double {
        /*
         * first, check that the agent has all the necessary attributes. otherwise, return a null to indicate that the agent cannot play the role
		 */
        if (organization.getNeeds(role.id).parallelStream().anyMatch { p -> organization.getHasValue(agent.id, p.id) == null }) {
            return MIN_SCORE
        }
        if (organization.getAchieves(role.id).contains(goal.goal)) {
            var score = MAX_SCORE
            var count = 0
            for (capability in organization.getRequires(role.id)) {
                score *= organization.getPossessesScore(agent.id, capability.id)
                if (java.lang.Double.compare(score, 0.0) == 0) {
                    /* short circuit */
                    return MIN_SCORE
                }
                count++
            }

            if (count < 2) {
                return score
            } else {
                val inverse = 1.0 / count
                return Math.pow(score, inverse)
            }
        }
        return MIN_SCORE
    }

    companion object {
        /**
         * `MIN_SCORE` is the minimum possible value for a score, which is {@value} .
         */
        const val MIN_SCORE = 0.0
        /**
         * `MAX_SCORE` is the maximum possible value for a score, which is {@value} .
         */
        const val MAX_SCORE = 1.0
    }

}
