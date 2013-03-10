/*
 * RequiresRelation.java
 *
 * Created on Mar 3, 2005
 *
 * See License.txt file the license agreement.
 */
package model.organization.relation;

import model.organization.entity.Capability;
import model.organization.entity.Role;

/**
 * Represents the requires relation, where a {@link Role} requires a
 * {@link Capability}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.2 $, $Date: 2010/05/20 21:16:42 $
 * @see Capability
 * @see Role
 * @since 1.0
 */
class RequiresRelation {
	/**
	 * The <code>Role</code> of this <code>RequiresRelation</code>.
	 */
	val Role role
	/**
	 * The <code>Capability</code> of this <code>RequiresRelation</code>.
	 */
	val Capability capability
	/**
	 * Optimization for hash code computation since it never changes.
	 */
	Integer hashCode = null
	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	String toString = null
	/**
	 * Constructs a new instance of <code>RequiresRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>RequiresRelation</code>.
	 * @param capability
	 *            the <code>Capability</code> of this
	 *            <code>RequiresRelation</code>.
	 */
	new( Role role,  Capability capability) {
		if (role == null || capability == null) {
			throw new IllegalArgumentException('''Parameters (role: «role», capability: «capability» cannot be null''')
		}
		this.role = role
		this.capability = capability
	}
	/**
	 * Returns the <code>Role</code> of this <code>RequiresRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>RequiresRelation</code>.
	 */
	def getRole() {
		role
	}
	/**
	 * Returns the <code>Capability</code> of this <code>RequiresRelation</code>
	 * .
	 *
	 * @return the <code>Capability</code> of this <code>RequiresRelation</code>
	 *         .
	 */
	def getCapability() {
		capability
	}
	override equals(Object object) {
		if (object instanceof RequiresRelation) {
			val requiresRelation = object as RequiresRelation
			return role.equals(requiresRelation.role) && capability.equals(requiresRelation.capability)
		}
		false
	}
	override hashCode() {
		if (hashCode == null) {
			hashCode = 1
			hashCode = 31 * hashCode + role.hashCode
			hashCode = 31 * hashCode + capability.hashCode
		}
		hashCode
	}
	override toString() {
		if (toString == null) {
			toString = '''«role.identifier» <-> «capability.identifier»'''
		}
		toString
	}

}