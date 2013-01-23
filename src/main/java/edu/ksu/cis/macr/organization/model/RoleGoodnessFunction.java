/*
 * RoleGoodnessFunction.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model;

import java.util.Collection;

import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Capability;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Role;
import model.organization.relation.Assignment;

/**
 * The <code>RoleGoodnessFunction</code> interface provides the necessary
 * functionality for computing the goodness score of how good an {@link Agent}
 * plays a {@link Role} to achieve a {@link InstanceGoal}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.8 $, $Date: 2012/01/20 21:24:38 $
 * @since 6.0
 */
public interface RoleGoodnessFunction {

	/**
	 * The <code>DefaultRoleGoodnessFunction</code> provides a very basic
	 * goodness function that only checks if the given <code>Agent</code> has
	 * the necessary attributes and then multiplies the scores of the
	 * capabilities.
	 * 
	 * @author Christopher Zhong
	 * @version $Revision: 1.1.2.8 $, $Date: 2012/01/20 21:24:38 $
	 * @since 6.0
	 */
	public static final class DefaultRoleGoodnessFunction implements
			RoleGoodnessFunction {

		@Override
		public double goodness(final Role role, final Agent<?> agent,
				final InstanceGoal<?> goal,
				final Collection<Assignment> assignments) {
			/*
			 * first, check that the agent has all the necessary attributes.
			 * otherwise, return a null to indicate that the agent cannot play
			 * the role
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

		@Override
		public String toString() {
			return "Default Role Goodness Function";
		}

	}

	/**
	 * <code>MIN_SCORE</code> is the minimum possible value for a score, which
	 * is {@value} .
	 */
	public static final double MIN_SCORE = 0.0;

	/**
	 * <code>MAX_SCORE</code> is the maximum possible value for a score, which
	 * is {@value} .
	 */
	public static final double MAX_SCORE = 1.0;

	/**
	 * Returns the score (<code>0.0</code> &le; score &le; <code>1.0</code>) of
	 * how good the given {@link Agent} plays the given {@link Role} to achieve
	 * the given {@link InstanceGoal}.
	 * 
	 * @param role
	 *            the {@link Role} to compute the goodness score.
	 * @param agent
	 *            the {@link Agent} to compute the goodness score.
	 * @param goal
	 *            the {@link InstanceGoal} that may be used in computing the
	 *            goodness score.
	 * @param assignments
	 *            the {@link Assignment} that may be used in computing the
	 *            goodness score.
	 * @return the goodness score (<code>0.0</code> &le; score &le;
	 *         <code>1.0</code>) of how good the given {@link Agent} plays the
	 *         given {@link Role} to achieve the given {@link InstanceGoal}. A
	 *         value of <code>0.0</code> indicates that the {@link Agent} cannot
	 *         perform the {@link Role} or the {@link InstanceGoal} cannot be
	 *         achieved by the {@link Role}.
	 */
	double goodness(Role role, Agent<?> agent, InstanceGoal<?> goal,
			Collection<Assignment> assignments);

}