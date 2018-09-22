package runtimemodels.chazm.api.function;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.InstanceGoal;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Assignment;

import java.util.Collection;

/**
 * The {@linkplain Goodness} interface defines the API for computing a score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how effective an
 * {@linkplain Agent} is at playing a {@linkplain Role} to achieve an {@linkplain InstanceGoal} in an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
@FunctionalInterface
public interface Goodness {

    /**
     * Returns a score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how effective an {@linkplain Agent} is at playing a {@linkplain Role} to achieve
     * an {@linkplain InstanceGoal} in an {@linkplain Organization}.
     *
     * @param organization the {@linkplain Organization}.
     * @param agent        the {@linkplain Agent}.
     * @param role         the {@linkplain Role}.
     * @param goal         the {@linkplain InstanceGoal}.
     * @param assignments  a set {@linkplain Assignment}s that may affect the score.
     * @return a score (<code>0.0</code> &le; score &le; <code>1.0</code>).
     */
    double compute(Organization organization, Agent agent, Role role, InstanceGoal goal, Collection<Assignment> assignments);

}
