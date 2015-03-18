package model.organization.function;

import java.util.Collection;

import javax.inject.Singleton;

import model.organization.Organization;
import model.organization.entity.Agent;
import model.organization.entity.Capability;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Role;
import model.organization.relation.Assignment;

@Singleton
class DefaultGoodness implements Goodness {

	@Override
	public double compute(final Organization organization, final Agent agent, final Role role, final InstanceGoal goal, final Collection<Assignment> assignments) {
		/*
		 * first, check that the agent has all the necessary attributes. otherwise, return a null to indicate that the agent cannot play the role
		 */
		if (organization.getNeeds(role.getId()).parallelStream().anyMatch(p -> organization.getHasValue(agent.getId(), p.getId()) == null)) {
			return MIN_SCORE;
		}
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

}