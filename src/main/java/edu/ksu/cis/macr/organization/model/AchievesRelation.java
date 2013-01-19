/*
 * AchievesRelation.java
 * 
 * Created on Mar 3, 2005
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model;

/**
 * Represents the achieves relation, where a {@link Role} can achieve a
 * {@link SpecificationGoal}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.4.2.2 $, $Date: 2010/07/30 19:19:35 $
 * @see Role
 * @see SpecificationGoal
 * @since 1.0
 */
public class AchievesRelation {

	/**
	 * Internal <code>String</code> for the formatting of
	 * <code>AchievesRelation</code> class.
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
	private int hashCode = 0;

	/**
	 * Constructs a new instance of <code>AchievesRelation</code>.
	 * 
	 * @param role
	 *            the <code>Role</code> of this <code>AchievesRelation</code>.
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> of this
	 *            <code>AchievesRelation</code>.
	 */
	AchievesRelation(final Role role, final SpecificationGoal specificationGoal) {
		if (role == null || specificationGoal == null) {
			throw new IllegalArgumentException(String.format(
					"Parameters (role: %s, goal: %s) cannot be null", role,
					specificationGoal));
		}
		this.role = role;
		this.specificationGoal = specificationGoal;
	}

	/**
	 * Returns the <code>Role</code> of this <code>AchievesRelation</code>.
	 * 
	 * @return the <code>Role</code> of this <code>AchievesRelation</code>.
	 */
	final Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>SpecificationGoal</code> of this
	 * <code>AchievesRelation</code>.
	 * 
	 * @return the <code>SpecificationGoal</code> of this
	 *         <code>AchievesRelation</code>.
	 */
	final SpecificationGoal getSpecificationGoal() {
		return specificationGoal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof AchievesRelation) {
			final AchievesRelation achievesRelation = (AchievesRelation) object;
			return getRole().equals(achievesRelation.getRole())
					&& getSpecificationGoal().equals(
							achievesRelation.getSpecificationGoal());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = getRole().hashCode() << 16
					| getSpecificationGoal().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return String.format(STRING_FORMAT, getRole().getIdentifier(),
				getSpecificationGoal().getIdentifier());
	}

}
