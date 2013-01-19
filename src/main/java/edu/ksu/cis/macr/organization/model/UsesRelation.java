/*
 * LinkedRelation.java
 * 
 * Created on May 18, 2010
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model;

/**
 * Represents the uses relation, where a {@link Role} uses a
 * {@link PerformanceFunction}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.1 $, $Date: 2010/06/10 20:25:45 $
 * @see Role
 * @see PerformanceFunction
 * @since 6.0
 */
public class UsesRelation {

	/**
	 * Internal <code>String</code> for the formatting of
	 * <code>UsesRelation</code> class.
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
	private int hashCode = 0;

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
	 *            the <code>PerformanceFunction</code> for the
	 *            <code>UsesRelation</code>.
	 */
	UsesRelation(final Role role, final PerformanceFunction performanceFunction) {
		this.role = role;
		this.performanceFunction = performanceFunction;
	}

	/**
	 * Returns the <code>Task</code>.
	 * 
	 * @return the <code>Task</code>.
	 */
	final Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>PerformanceFunction</code>.
	 * 
	 * @return the <code>PerformanceFunction</code>.
	 */
	final PerformanceFunction getPerformanceFunction() {
		return performanceFunction;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof UsesRelation) {
			final UsesRelation linkedRelation = (UsesRelation) object;
			return getRole().equals(linkedRelation.getRole())
					&& getPerformanceFunction().equals(
							linkedRelation.getPerformanceFunction());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = getRole().hashCode() << 16
					| getPerformanceFunction().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getIdentifier(),
					getPerformanceFunction().getIdentifier());
		}
		return toString;
	}

}
