package org.models.organization.function;

import java.util.Collection;

import org.models.organization.entity.Agent;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.Capability;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Role;
import org.models.organization.relation.Assignment;

/**
 * The <code>DefaultRoleGoodnessFunction</code> provides a very basic goodness function that only checks if the given <code>Agent</code> has the necessary
 * attributes and then multiplies the scores of the capabilities.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public final class DefaultRoleGoodnessFunction implements RoleGoodnessFunction {
	@Override
	public double goodness(final Role role, final Agent agent, final InstanceGoal<?> goal, final Collection<Assignment> assignments) {
		/*
		 * first, check that the agent has all the necessary attributes. otherwise, return a null to indicate that the agent cannot play the role
		 */
		for (final Attribute attribute : role.getNeedsSet()) {
			if (agent.has(attribute) == null) {
				return MIN_SCORE;
			}
		}
		double score = MAX_SCORE;
		int count = 0;
		for (final Capability capability : role.getRequiresSet()) {
			final double possessesScore = agent.possesses(capability);
			score *= possessesScore;
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