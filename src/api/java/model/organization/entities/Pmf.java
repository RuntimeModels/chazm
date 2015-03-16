package model.organization.entities;

import java.util.Collection;

import model.organization.Organization;
import model.organization.id.Identifiable;
import model.organization.relations.Assignment;

/**
 * The {@linkplain Pmf} interface defines the performance function entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public interface Pmf extends Identifiable<Pmf> {
	/**
	 * Computes the PMF for the given <code>Agent</code>, <code>Role</code>, and <code>Goal</code>.
	 *
	 * @param agent
	 *            the <code>Agent</code> to compute the PMF.
	 * @param role
	 *            the <code>Role</code> to compute the PMF.
	 * @param goal
	 *            the <code>Goal</code> to compute the PMF.
	 * @param assignments
	 *            the {@link Assignment} that may be used in computing the PMF.
	 * @return a <code>Double</code> value indicating the PMF value, or <code>null</code> if not applicable.
	 */
	Double pmf(Agent agent, Role role, InstanceGoal goal, Collection<Assignment> assignments);
}
