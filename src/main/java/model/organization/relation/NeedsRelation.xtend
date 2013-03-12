/*
 * NeedsRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.relation

import model.organization.entity.Role
import model.organization.entity.Attribute

/**
 * Represents the needs relation, where a {@link Role} needs an
 * {@link Attribute}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.2 $, $Date: 2010/06/23 22:15:50 $
 * @since 6.0
 */
class NeedsRelation {
	/**
	 * The <code>Role</code> of this <code>NeedsRelation</code>.
	 */
	val Role role
	/**
	 * The <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	val Attribute attribute
	/**
	 * Optimization for hash code computation since it never changes.
	 */
	Integer hashCode = null
	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	String toString = null
	/**
	 * Constructs a new instance of <code>NeedsRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>NeedsRelation</code>.
	 * @param attribute
	 *            the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	new(Role role, Attribute attribute) {
		if (role == null || attribute == null) {
			throw new IllegalArgumentException('''Parameters (role: «role», attribute: «attribute») cannot be null''')
		}
		this.role = role
		this.attribute = attribute
	}
	/**
	 * Returns the <code>Role</code> of this <code>NeedsRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>NeedsRelation</code>.
	 */
	def Role getRole() {
		role
	}
	/**
	 * Returns the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 *
	 * @return the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	def Attribute getAttribute() {
		attribute
	}
	override boolean equals(Object object) {
		if (object instanceof NeedsRelation) {
			val needsRelation = object as NeedsRelation
			return role.equals(needsRelation.role) && attribute.equals(needsRelation.attribute)
		}
		false
	}
	override int hashCode() {
		if (hashCode == null) {
			hashCode = 1
			hashCode = 31 * hashCode + role.hashCode
			hashCode = 31 * hashCode + attribute.hashCode
		}
		hashCode
	}
	override String toString() {
		if (toString == null) {
			toString = '''«role.identifier» <-> «attribute.identifier»'''
		}
		toString
	}
}