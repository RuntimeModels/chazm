/**
 * AchievesRelation.java
 *
 * Created on Mar 3, 2005
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;

/**
 * Represents the achieves relation, where a {@link Role} can achieve a {@link SpecificationGoal}.
 *
 * @author Christopher Zhong
 * @see Role
 * @see SpecificationGoal
 * @since 1.0
 */
public class AchievesRelation {

	/**
	 * Internal <code>String</code> for the format of the <code>IllegalArgumentException</code>.
	 */
	private static final String EXCEPTION_FORMAT = "Parameters (role: %s, goal: %s) cannot be null";

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The <code>Role</code> of this <code>AchievesRelation</code>.
	 */
	private final Role role;

	/**
	 * The <code>SpecificationGoal</code> of this <code>AchievesRelation</code>.
	 */
	private final SpecificationGoal specificationGoal;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>AchievesRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>AchievesRelation</code>.
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> of this <code>AchievesRelation</code>.
	 */
	public AchievesRelation(final Role role, final SpecificationGoal specificationGoal) {
		if (role == null || specificationGoal == null) {
			throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, role, specificationGoal));
		}
		this.role = role;
		this.specificationGoal = specificationGoal;
	}

	/**
	 * Returns the <code>Role</code> of this <code>AchievesRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>AchievesRelation</code>.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>SpecificationGoal</code> of this <code>AchievesRelation</code>.
	 *
	 * @return the <code>SpecificationGoal</code> of this <code>AchievesRelation</code>.
	 */
	public SpecificationGoal getSpecificationGoal() {
		return specificationGoal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof AchievesRelation) {
			final AchievesRelation achievesRelation = (AchievesRelation) object;
			return getRole().equals(achievesRelation.getRole()) && getSpecificationGoal().equals(achievesRelation.getSpecificationGoal());

		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getSpecificationGoal().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getId(), getSpecificationGoal().getIdentifier());
		}
		return toString;
	}
}
