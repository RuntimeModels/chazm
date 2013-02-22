/*
 * ContainsRelation.java
 *
 * Created on May 25, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.relation

import model.organization.entity.Role
import model.organization.entity.Characteristic

/**
 * The <code>ContainsRelation</code> class represents the contains relation,
 * where a {@link Role} contains a {@link Characteristic}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.1 $, $Date: 2010/05/25 22:18:33 $
 * @since 6.0
 */
class ContainsRelation {
	/**
	 * The <code>Role</code> of this <code>ContainsRelation</code>.
	 */
	val Role role

	/**
	 * The <code>Characteristic</code> of this <code>ContainsRelation</code>.
	 */
	val Characteristic characteristic

	/**
	 * The <code>double</code> value of this <code>ContainsRelation</code>.
	 */
	var double value

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	Integer hashCode = null
	/**
	 * Constructs a new instance of <code>ContainsRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>ContainsRelation</code>.
	 * @param characteristic
	 *            the <code>Characteristic</code> of this
	 *            <code>ContainsRelation</code>.
	 * @param value
	 */
	new(Role role, Characteristic characteristic,  double value) {
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException('''Parameters (role: «role», characteristic: «characteristic») cannot be null''')
		}
		this.role = role
		this.characteristic = characteristic
		this.value = value
	}
	/**
	 * Returns the <code>Role</code> of this <code>ContainsRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>ContainsRelation</code>.
	 */
	def Role getRole() {
		role
	}
	/**
	 * Returns the <code>Characteristic</code> of this
	 * <code>ContainsRelation</code>.
	 *
	 * @return the <code>Characteristic</code> of this
	 *         <code>ContainsRelation</code>.
	 */
	def getCharacteristic() {
		characteristic
	}

	/**
	 * Returns the <code>double</code> value of this
	 * <code>ContainsRelation</code>.
	 *
	 * @return the <code>double</code> value of this
	 *         <code>ContainsRelation</code>.
	 */
	def getValue() {
		value
	}
	/**
	 * Sets the value of this <code>ContainsRelation</code> to the given
	 * <code>double</code> value.
	 *
	 * @param value
	 *            the <code>double</code> value to set.
	 */
	def void setValue(double value) {
		this.value = value;
	}
	override equals(Object object) {
		if (object instanceof ContainsRelation) {
			val containsRelation = object as ContainsRelation
			return role.equals(containsRelation.role) && characteristic.equals(containsRelation.characteristic)
		}
		false
	}
	override hashCode() {
		if (hashCode == null) {
			hashCode = 1
			hashCode = 31 * hashCode + role.hashCode
			hashCode = 31 * hashCode + characteristic.hashCode
		}
		hashCode
	}
	override toString() {
		'''«role.identifier» <-> «characteristic.identifier» : «value»'''
	}
}