package model.organization.function;

import java.util.Collection;

import model.organization.Organization;
import model.organization.relation.Assignment;

/**
 * The {@linkplain Effectiveness} interface defines the APIs determining how effective an {@linkplain Organization} is in achieving the goals it set out to
 * achieve.
 *
 * @author Christopher Zhong
 * @see <a href="http://en.wikipedia.org/wiki/Organizational_effectiveness">http://en.wikipedia.org/wiki/Organizational_effectiveness</a>
 * @since 3.2
 */
@FunctionalInterface
public interface Effectiveness {

	/**
	 * Returns a <code>double</code>-valued score of an {@linkplain Organization}'s effectiveness.
	 *
	 * @param organization
	 *            the {@linkplain Organization}.
	 * @return a <code>double</code>-valued score.
	 */
	default double compute(final Organization organization) {
		return compute(organization, organization.getAssignments());
	}

	/**
	 * Returns a <code>double</code>-valued score of an {@linkplain Organization}'s effectiveness.
	 *
	 * @param organization
	 *            the {@linkplain Organization}.
	 * @param assignments
	 *            a set of {@linkplain Assignment}s.
	 * @return a <code>double</code>-valued score.
	 */
	double compute(Organization organization, Collection<Assignment> assignments);

}