/*
 * UsesRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.relation

import model.organization.entity.Role
import model.organization.entity.PerformanceFunction

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
class UsesRelation {
	/**
	 * The <code>Role</code> of this <code>UsesRelation</code>.
	 */
	val Role role

	/**
	 * The <code>PerformanceFunction</code> of this <code>UsesRelation</code>.
	 */
	val PerformanceFunction performanceFunction

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	Integer hashCode = null

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	String toString = null
	/**
	 * Constructs a new instance of <code>UsesRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> for the <code>UsesRelation</code>.
	 * @param performanceFunction
	 *            the <code>PerformanceFunction</code> for the
	 *            <code>UsesRelation</code>.
	 */
	new(Role role, PerformanceFunction performanceFunction) {
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException('''Parameters (role: «role», function: «performanceFunction»)''')
		}
		this.role = role;
		this.performanceFunction = performanceFunction;
	}

	/**
	 * Returns the <code>Task</code>.
	 *
	 * @return the <code>Task</code>.
	 */
	def getRole() {
		role
	}

	/**
	 * Returns the <code>PerformanceFunction</code>.
	 *
	 * @return the <code>PerformanceFunction</code>.
	 */
	def getPerformanceFunction() {
		performanceFunction
	}
	override equals(Object object) {
		if (object instanceof UsesRelation) {
			val usesRelation = object as UsesRelation
			return role.equals(usesRelation.role) && performanceFunction.equals(usesRelation.performanceFunction)
		}
		false
	}
	override hashCode() {
		if (hashCode == null) {
			hashCode = 1
			hashCode = 31 * hashCode + role.hashCode
			hashCode = 31 * hashCode + performanceFunction.hashCode
		}
		hashCode
	}
	override toString() {
		if (toString == null)
			toString = '''«role.identifier» <-> «performanceFunction.identifier»'''
		toString
	}
}