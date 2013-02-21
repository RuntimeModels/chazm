package model.organization.relation

import model.organization.entity.Role
import model.organization.entity.SpecificationGoal

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
class AchievesRelation {
	/**
	 * The <code>Role</code> of this <code>AchievesRelation</code>.
	 */
	val Role role
	/**
	 * The <code>SpecificationGoal</code> of this <code>AchievesRelation</code>.
	 */
	val SpecificationGoal specificationGoal
	/**
	 * Optimization for hash code computation since it never changes.
	 */
	int hashCode = 0
	/**
	 * Constructs a new instance of <code>AchievesRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>AchievesRelation</code>.
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> of this
	 *            <code>AchievesRelation</code>.
	 */
	new(Role role, SpecificationGoal specificationGoal) {
		if (role == null || specificationGoal == null) {
			throw new IllegalArgumentException('''Parameters (role: «role», goal: «specificationGoal»)''')
		}
		this.role = role
		this.specificationGoal = specificationGoal
	}
	/**
	 * Returns the <code>Role</code> of this <code>AchievesRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>AchievesRelation</code>.
	 */
	def getRole() {
		role
	}
	/**
	 * Returns the <code>SpecificationGoal</code> of this
	 * <code>AchievesRelation</code>.
	 *
	 * @return the <code>SpecificationGoal</code> of this
	 *         <code>AchievesRelation</code>.
	 */
	def getSpecificationGoal() {
		specificationGoal
	}
	override equals(Object object) {
		if (object instanceof AchievesRelation) {
			val achievesRelation = object as AchievesRelation
			return role.equals(achievesRelation.role) && specificationGoal.equals(achievesRelation.specificationGoal)
		}
		false
	}
	override hashCode() {
		if (hashCode == 0) {
//			hashCode = role.hashCode << 16 | specificationGoal.hashCode
		}
		hashCode
	}
	override toString() {
		'''«role.identifier» <-> «specificationGoal.identifier»'''
	}
}