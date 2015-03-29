package model.organization.relation;

import model.organization.Organization;
import model.organization.entity.Characteristic;
import model.organization.entity.Role;

/**
 * The {@linkplain Contains} interface defines the contains relation, where a {@linkplain Role} contains a {@linkplain Characteristic}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Contains {
	/**
	 * Returns the {@linkplain Role} of this {@linkplain Contains}.
	 *
	 * @return the {@linkplain Role} of this {@linkplain Contains}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain Characteristic} of this {@linkplain Contains}.
	 *
	 * @return the {@linkplain Characteristic} of this {@linkplain Contains}.
	 */
	Characteristic getCharacteristic();

	/**
	 * Returns the <code>double</code> value of this {@linkplain Contains}.
	 *
	 * @return the <code>double</code> value of this {@linkplain Contains}.
	 */
	double getValue();

	/**
	 * Sets the value of this {@linkplain Contains} to the given <code>double</code> value.
	 *
	 * @param value
	 *            the <code>double</code> value to set.
	 */
	void setValue(double value);
}