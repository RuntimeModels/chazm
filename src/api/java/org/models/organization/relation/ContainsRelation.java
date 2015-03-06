/**
 * ContainsRelation.java
 *
 * Created on May 25, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.entity.Characteristic;
import org.models.organization.entity.Role;

/**
 * The <code>ContainsRelation</code> class represents the contains relation, where a {@link Role} contains a {@link Characteristic}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public class ContainsRelation {

	/**
	 * Internal <code>String</code> for the format of the <code>IllegalArgumentException</code>.
	 */
	private static final String EXCEPTION_FORMAT = "Parameters (role: %s, characteristic: %s) cannot be null";

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s: %f";

	/**
	 * The <code>Role</code> of this <code>ContainsRelation</code>.
	 */
	private final Role role;

	/**
	 * The <code>Characteristic</code> of this <code>ContainsRelation</code>.
	 */
	private final Characteristic characteristic;

	/**
	 * The <code>double</code> value of this <code>ContainsRelation</code>.
	 */
	private double value;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Constructs a new instance of <code>ContainsRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>ContainsRelation</code>.
	 * @param characteristic
	 *            the <code>Characteristic</code> of this <code>ContainsRelation</code>.
	 * @param value
	 */
	public ContainsRelation(final Role role, final Characteristic characteristic, final double value) {
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, role, characteristic));
		}
		this.role = role;
		this.characteristic = characteristic;
		this.value = value;
	}

	/**
	 * Returns the <code>Role</code> of this <code>ContainsRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>ContainsRelation</code>.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>Characteristic</code> of this <code>ContainsRelation</code>.
	 *
	 * @return the <code>Characteristic</code> of this <code>ContainsRelation</code>.
	 */
	public Characteristic getCharacteristic() {
		return characteristic;
	}

	/**
	 * Returns the <code>double</code> value of this <code>ContainsRelation</code>.
	 *
	 * @return the <code>double</code> value of this <code>ContainsRelation</code>.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value of this <code>ContainsRelation</code> to the given <code>double</code> value.
	 *
	 * @param value
	 *            the <code>double</code> value to set.
	 */
	public void setValue(final double value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof ContainsRelation) {
			final ContainsRelation containsRelation = (ContainsRelation) object;
			return getRole().equals(containsRelation.getRole()) && getCharacteristic().equals(containsRelation.getCharacteristic());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getCharacteristic().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return String.format(STRING_FORMAT, getRole().getIdentifier(), getCharacteristic().getIdentifier(), getValue());
	}
}
