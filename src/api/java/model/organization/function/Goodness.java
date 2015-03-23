package model.organization.function;

import java.util.Collection;

import model.organization.Organization;
import model.organization.entity.Agent;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Role;
import model.organization.relation.Assignment;

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
	 * <code>MIN_SCORE</code> is the minimum possible value for a score, which is {@value} .
	 */
	public static final double MIN_SCORE = 0.0;
	/**
	 * <code>MAX_SCORE</code> is the maximum possible value for a score, which is {@value} .
	 */
	public static final double MAX_SCORE = 1.0;

	/**
	 * Returns a score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how effective an {@linkplain Agent} is at playing a {@linkplain Role} to achieve
	 * an {@linkplain InstanceGoal} in an {@linkplain Organization}.
	 *
	 * @param organization
	 *            the {@linkplain Organization}.
	 * @param agent
	 *            the {@linkplain Agent}.
	 * @param role
	 *            the {@linkplain Role}.
	 * @param goal
	 *            the {@linkplain InstanceGoal}.
	 * @param assignments
	 *            a set {@linkplain Assignment}s that may affect the score.
	 *
	 * @return a score (<code>0.0</code> &le; score &le; <code>1.0</code>).
	 */
	double compute(Organization organization, Agent agent, Role role, InstanceGoal goal, Collection<Assignment> assignments);

}