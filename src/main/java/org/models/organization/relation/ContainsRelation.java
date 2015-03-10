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
 * The {@linkplain ContainsRelation} class is an implementation of the {@linkplain Contains}.
 *
 * @author Christopher Zhong
 * @see Contains
 * @since 6.0
 */
public class ContainsRelation implements Contains {
	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s: %f";

	/**
	 * The {@linkplain Role} of this {@linkplain Contains}.
	 */
	private final Role role;

	/**
	 * The {@linkplain Characteristic} of this {@linkplain Contains}.
	 */
	private final Characteristic characteristic;

	/**
	 * The <code>double</code> value of this {@linkplain Contains}.
	 */
	private double value;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Constructs a new instance of {@linkplain Contains}.
	 *
	 * @param role
	 *            the {@linkplain Role} of this {@linkplain Contains}.
	 * @param characteristic
	 *            the {@linkplain Characteristic} of this {@linkplain Contains}.
	 * @param value
	 *            the <code>double</code> value of this {@linkplain Contains}.
	 */
	public ContainsRelation(final Role role, final Characteristic characteristic, final double value) {
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		if (characteristic == null) {
			throw new IllegalArgumentException("Parameter (characteristic) cannot be null");
		}
		this.role = role;
		this.characteristic = characteristic;
		this.value = value;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Characteristic getCharacteristic() {
		return characteristic;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(final double value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Contains) {
			final Contains contains = (Contains) object;
			return getRole().equals(contains.getRole()) && getCharacteristic().equals(contains.getCharacteristic());
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
		return String.format(STRING_FORMAT, getRole().getId(), getCharacteristic().getId(), getValue());
	}
}
