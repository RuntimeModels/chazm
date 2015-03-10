/**
 * ModeratesRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.Messages;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.PerformanceFunction;

/**
 * The {@linkplain ModeratesRelation} class is an implementation of the {@linkplain Moderates}.
 *
 * @author Christopher Zhong
 * @see Moderates
 * @since 6.0
 */
public class ModeratesRelation implements Moderates {
	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The {@linkplain PerformanceFunction} of this {@linkplain Moderates}.
	 */
	private final PerformanceFunction performanceFunction;

	/**
	 * The {@linkplain Attribute} of this {@linkplain Moderates}.
	 */
	private final Attribute attribute;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of {@linkplain Moderates}.
	 *
	 * @param performanceFunction
	 *            the {@linkplain PerformanceFunction} of this {@linkplain Moderates}.
	 * @param attribute
	 *            the {@linkplain Attribute} of this {@linkplain Moderates}.
	 */
	public ModeratesRelation(final PerformanceFunction performanceFunction, final Attribute attribute) {
		if (performanceFunction == null) {
			throw new IllegalArgumentException(String.format(Messages.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "performanceFunction"));
		}
		if (attribute == null) {
			throw new IllegalArgumentException(String.format(Messages.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "attribute"));
		}
		this.performanceFunction = performanceFunction;
		this.attribute = attribute;
	}

	@Override
	public PerformanceFunction getPerformanceFunction() {
		return performanceFunction;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Moderates) {
			final Moderates moderates = (Moderates) object;
			return getPerformanceFunction().equals(moderates.getPerformanceFunction()) && getAttribute().equals(moderates.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getPerformanceFunction().hashCode() << 16 | getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getPerformanceFunction().getId(), getAttribute().getId());
		}
		return toString;
	}
}
