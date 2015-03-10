/*
 * PerformanceFunction.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Collection;
import java.util.Set;

import org.models.organization.Organization;
import org.models.organization.identifier.Identifiable;
import org.models.organization.relation.Assignment;

/**
 * The {@linkplain PerformanceFunction} interface defines the performance function entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public interface PerformanceFunction extends Identifiable {
	/**
	 * Returns the set of <code>Role</code> that are linked to this <code>PerformanceFunction</code>.
	 *
	 * @return the set of <code>Role</code> that are linked to this <code>PerformanceFunction</code>.
	 */
	Set<Role> getUsedBySet();

	/**
	 * Sets the given <code>Attribute</code> as the <code>Attribute</code> that this <code>PerformanceFunction</code> moderates.
	 *
	 * @param attribute
	 *            the <code>Attribute</code> to set.
	 */
	void setModerates(Attribute attribute);

	/**
	 * Returns the <code>Attribute</code> that this <code>PerformanceFunction</code> moderates.
	 *
	 * @return the <code>Attribute</code> that this <code>PerformanceFunction</code> moderates.
	 */
	Attribute getModerates();

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
	Double pmf(Agent agent, Role role, InstanceGoal<?> goal, Collection<Assignment> assignments);

}
