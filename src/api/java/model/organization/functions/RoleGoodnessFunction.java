/*
 * RoleGoodnessFunction.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.functions;

import java.util.Collection;

import model.organization.entities.Agent;
import model.organization.entities.InstanceGoal;
import model.organization.entities.Role;
import model.organization.relations.Assignment;

/**
 * The <code>RoleGoodnessFunction</code> interface provides the necessary functionality for computing the goodness score of how good an {@link Agent} plays a
 * {@link Role} to achieve a {@link InstanceGoal}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public interface RoleGoodnessFunction {

	/**
	 * <code>MIN_SCORE</code> is the minimum possible value for a score, which is {@value} .
	 */
	public static final double MIN_SCORE = 0.0;

	/**
	 * <code>MAX_SCORE</code> is the maximum possible value for a score, which is {@value} .
	 */
	public static final double MAX_SCORE = 1.0;

	/**
	 * Returns the score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how good the given {@link Agent} plays the given {@link Role} to achieve the
	 * given {@link InstanceGoal}.
	 *
	 * @param role
	 *            the {@link Role} to compute the goodness score.
	 * @param agent
	 *            the {@link Agent} to compute the goodness score.
	 * @param goal
	 *            the {@link InstanceGoal} that may be used in computing the goodness score.
	 * @param assignments
	 *            the {@link Assignment} that may be used in computing the goodness score.
	 * @return the goodness score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how good the given {@link Agent} plays the given {@link Role} to
	 *         achieve the given {@link InstanceGoal}. A value of <code>0.0</code> indicates that the {@link Agent} cannot perform the {@link Role} or the
	 *         {@link InstanceGoal} cannot be achieved by the {@link Role}.
	 */
	double goodness(Role role, Agent agent, InstanceGoal goal, Collection<Assignment> assignments);

}