/*
 * ModeratesRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.relation;

import model.organization.entity.Attribute;
import model.organization.entity.PerformanceFunction;

/**
 * Represents the moderates relation, where a {@link PerformanceFunction}
 * moderates an {@link Attribute}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.3 $, $Date: 2010/06/24 19:26:06 $
 * @see PerformanceFunction
 * @see Attribute
 * @since 6.0
 */
public class ModeratesRelation {

	/**
	 * Internal <code>String</code> for the formatting of
	 * <code>ModeratesRelation</code> class.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The <code>PerformanceFunction</code> of this
	 * <code>ModeratesRelation</code>.
	 */
	private final PerformanceFunction performanceFunction;

	/**
	 * The <code>Attribute</code> of this <code>ModeratesRelation</code>.
	 */
	private final Attribute attribute;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private int hashCode = 0;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>ModeratesRelation</code>.
	 * 
	 * @param performanceFunction
	 *            the <code>Role</code> of this <code>ModeratesRelation</code>.
	 * @param attribute
	 *            the <code>Attribute</code> of this
	 *            <code>ModeratesRelation</code>.
	 */
	public ModeratesRelation(final PerformanceFunction performanceFunction,
			final Attribute attribute) {
		if (performanceFunction == null || attribute == null) {
			throw new IllegalArgumentException(
					String.format(
							"Parameters (performanceFunction: %s, attribute: %s) cannot be null",
							performanceFunction, attribute));
		}
		this.performanceFunction = performanceFunction;
		this.attribute = attribute;
	}

	/**
	 * Returns the <code>PerformanceFunction</code> of this
	 * <code>ModeratesRelation</code>.
	 * 
	 * @return the <code>PerformanceFunction</code> of this
	 *         <code>ModeratesRelation</code>.
	 */
	public final PerformanceFunction getPerformanceFunction() {
		return performanceFunction;
	}

	/**
	 * Returns the <code>Attribute</code> of this <code>ModeratesRelation</code>
	 * .
	 * 
	 * @return the <code>Attribute</code> of this <code>ModeratesRelation</code>
	 *         .
	 */
	public final Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof ModeratesRelation) {
			final ModeratesRelation moderatesRelation = (ModeratesRelation) object;
			return getPerformanceFunction().equals(
					moderatesRelation.getPerformanceFunction())
					&& getAttribute().equals(moderatesRelation.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = getPerformanceFunction().hashCode() << 16
					| getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getPerformanceFunction()
					.getIdentifier(), getAttribute().getIdentifier());
		}
		return toString;
	}

}
