/**
 * UsesRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.M;
import org.models.organization.entity.PerformanceFunction;
import org.models.organization.entity.Role;

/**
 * The {@linkplain UsesRelation} class is an implementation of the {@linkplain Uses}.
 *
 * @author Christopher Zhong
 * @see Uses
 * @since 6.0
 */
public class UsesRelation implements Uses {

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The {@linkplain Role} of this {@linkplain Uses}.
	 */
	private final Role role;

	/**
	 * The {@linkplain PerformanceFunction} of this {@linkplain Uses}.
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
	 * Constructs a new instance of {@linkplain Uses}.
	 *
	 * @param role
	 *            the {@linkplain Role} of this {@linkplain Uses}.
	 * @param performanceFunction
	 *            the {@linkplain PerformanceFunction} of this {@linkplain Uses}.
	 */
	public UsesRelation(final Role role, final PerformanceFunction performanceFunction) {
		if (role == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "role"));
		}
		if (performanceFunction == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "performanceFunction"));
		}
		this.role = role;
		this.performanceFunction = performanceFunction;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public PerformanceFunction getPerformanceFunction() {
		return performanceFunction;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof UsesRelation) {
			final Uses usesRelation = (Uses) object;
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
			toString = String.format(STRING_FORMAT, getRole().getId(), getPerformanceFunction().getId());
		}
		return toString;
	}
}
