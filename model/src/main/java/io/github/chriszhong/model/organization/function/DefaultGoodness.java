package io.github.chriszhong.model.organization.function;

import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.function.Goodness;
import io.github.runtimemodels.chazm.relation.Assignment;

import java.util.Collection;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
class DefaultGoodness implements Goodness {

	@Override
	public double compute(@NotNull final Organization organization, @NotNull final Agent agent, @NotNull final Role role, @NotNull final InstanceGoal goal,
			@NotNull final Collection<Assignment> assignments) {
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