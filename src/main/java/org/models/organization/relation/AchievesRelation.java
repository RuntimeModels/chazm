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
 * The {@linkplain AchievesRelation} class is an implementation of the {@linkplain Achieves}.
 *
 * @author Christopher Zhong
 * @see Achieves
 * @since 1.0
 */
public class AchievesRelation implements Achieves {
	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The {@linkplain Role} of this {@linkplain Achieves}.
	 */
	private final Role role;

	/**
	 * The {@linkplain SpecificationGoal} of this {@linkplain Achieves}.
	 */
	private final SpecificationGoal goal;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of {@linkplain Achieves}.
	 *
	 * @param role
	 *            the {@linkplain Role} of this {@linkplain Achieves}.
	 * @param goal
	 *            the {@linkplain SpecificationGoal} of this {@linkplain Achieves}.
	 */
	public AchievesRelation(final Role role, final SpecificationGoal goal) {
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		if (goal == null) {
			throw new IllegalArgumentException("Parameter (specificationGoal) cannot be null");
		}
		this.role = role;
		this.goal = goal;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public SpecificationGoal getGoal() {
		return goal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Achieves) {
			final Achieves achieves = (Achieves) object;
			return getRole().equals(achieves.getRole()) && getGoal().equals(achieves.getGoal());

		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getGoal().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getId(), getGoal().getId());
		}
		return toString;
	}
}
