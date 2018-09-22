package runtimemodels.chazm.model.function;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.entity.InstanceGoal;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.function.Goodness;
import runtimemodels.chazm.api.relation.Assignment;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Singleton
class DefaultGoodness implements Goodness {

    /**
     * <code>MIN_SCORE</code> is the minimum possible value for a score, which is {@value} .
     */
    static final double MIN_SCORE = 0.0;
    /**
     * <code>MAX_SCORE</code> is the maximum possible value for a score, which is {@value} .
     */
    static final double MAX_SCORE = 1.0;

    @Override
    public double compute(@NotNull final Organization organization, @NotNull final Agent agent, @NotNull final Role role, @NotNull final InstanceGoal goal, @NotNull final Collection<Assignment> assignments) {
        /*
         * first, check that the agent has all the necessary attributes. otherwise, return a null to indicate that the agent cannot play the role
		 */
        if (organization.getNeeds(role.getId()).parallelStream().anyMatch(p -> organization.getHasValue(agent.getId(), p.getId()) == null)) {
            return MIN_SCORE;
        }
        if (organization.getAchieves(role.getId()).contains(goal.getGoal())) {
            double score = MAX_SCORE;
            int count = 0;
            for (final Capability capability : organization.getRequires(role.getId())) {
                score *= organization.getPossessesScore(agent.getId(), capability.getId());
                if (Double.compare(score, 0.0) == 0) {
                    /* short circuit */
                    return MIN_SCORE;
                }
                count++;
            }

            if (count < 2) {
                return score;
            } else {
                final double inverse = 1d / count;
                return Math.pow(score, inverse);
            }
        }
        return MIN_SCORE;
    }

}
