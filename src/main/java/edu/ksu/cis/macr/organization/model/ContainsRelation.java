/*
 * ContainsRelation.java
 * 
 * Created on May 25, 2010
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model;

/**
 * The <code>ContainsRelation</code> class represents the contains relation,
 * where a {@link Role} contains a {@link Characteristic}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.1 $, $Date: 2010/05/25 22:18:33 $
 * @since 6.0
 */
public class ContainsRelation {

	/**
	 * Internal <code>String</code> for the formatting of
	 * <code>HasRelation</code> class.
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
	private int hashCode = 0;

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
	ContainsRelation(final Role role, final Characteristic characteristic,
			final double value) {
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format(
					"Paramters (role: %s, characteristic: %s) cannot be null",
					role, characteristic));
		}
		this.role = role;
		this.characteristic = characteristic;
		setValue(value);
	}

	/**
	 * Returns the <code>Role</code> of this <code>ContainsRelation</code>.
	 * 
	 * @return the <code>Role</code> of this <code>ContainsRelation</code>.
	 */
	final Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>Characteristic</code> of this
	 * <code>ContainsRelation</code>.
	 * 
	 * @return the <code>Characteristic</code> of this
	 *         <code>ContainsRelation</code>.
	 */
	final Characteristic getCharacteristic() {
		return characteristic;
	}

	/**
	 * Returns the <code>double</code> value of this
	 * <code>ContainsRelation</code>.
	 * 
	 * @return the <code>double</code> value of this
	 *         <code>ContainsRelation</code>.
	 */
	final double getValue() {
		return value;
	}

	/**
	 * Sets the value of this <code>ContainsRelation</code> to the given
	 * <code>double</code> value.
	 * 
	 * @param value
	 *            the <code>double</code> value to set.
	 */
	final void setValue(final double value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof ContainsRelation) {
			final ContainsRelation containsRelation = (ContainsRelation) object;
			return getRole().equals(containsRelation.getRole())
					&& getCharacteristic().equals(
							containsRelation.getCharacteristic());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = getRole().hashCode() << 16
					| getCharacteristic().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return String.format(STRING_FORMAT, getRole().getIdentifier(),
				getCharacteristic().getIdentifier(), getValue());
	}

}
