/**
 * UsesRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.entity.PerformanceFunction;
import org.models.organization.entity.Role;

/**
 * Represents the uses relation, where a {@link Role} uses a {@link PerformanceFunction}.
 *
 * @author Christopher Zhong
 * @see Role
 * @see PerformanceFunction
 * @since 6.0
 */
public class UsesRelation {
	/**
	 * Internal <code>String</code> for the format of the <code>IllegalArgumentException</code>.
	 */
	private static final String EXCEPTION_FORMAT = "Parameters (role: %s, performanceFunction: %s) cannot be null";

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The <code>Role</code> of this <code>UsesRelation</code>.
	 */
	private final Role role;

	/**
	 * The <code>PerformanceFunction</code> of this <code>UsesRelation</code>.
	 */
	private final PerformanceFunction performanceFunction;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>UsesRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> for the <code>UsesRelation</code>.
	 * @param performanceFunction
	 *            the <code>PerformanceFunction</code> for the <code>UsesRelation</code>.
	 */
	public UsesRelation(final Role role, final PerformanceFunction performanceFunction) {
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, role, performanceFunction));
		}
		this.role = role;
		this.performanceFunction = performanceFunction;
	}

	/**
	 * Returns the <code>Task</code>.
	 *
	 * @return the <code>Task</code>.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>PerformanceFunction</code>.
	 *
	 * @return the <code>PerformanceFunction</code>.
	 */
	public PerformanceFunction getPerformanceFunction() {
		return performanceFunction;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof UsesRelation) {
			final UsesRelation usesRelation = (UsesRelation) object;
			return getRole().equals(usesRelation.getRole()) && getPerformanceFunction().equals(usesRelation.getPerformanceFunction());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getPerformanceFunction().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getId(), getPerformanceFunction().getIdentifier());
		}
		return toString;
	}
}
