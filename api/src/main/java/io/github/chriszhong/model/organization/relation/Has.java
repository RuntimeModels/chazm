package io.github.chriszhong.model.organization.relation;

import io.github.chriszhong.model.organization.Organization;
import io.github.chriszhong.model.organization.entity.Agent;
import io.github.chriszhong.model.organization.entity.Attribute;

/**
 * The {@linkplain Has} interface defines the has relation, where an {@linkplain Agent} has an {@linkplain Attribute}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Has {
	/**
	 * <code>QUALITY_MIN_AMOUNT</code> is the minimum possible value for a quality-type score, which is {@value} .
	 */
	static final double QUALITY_MIN_AMOUNT = 0.0;
	/**
	 * <code>QUALITY_MAX_AMOUNT</code> is the maximum possible value for a quality-type score, which is {@value} .
	 */
	static final double QUALITY_MAX_AMOUNT = 1.0;
	/**
	 * <code>QUANTITY_MIN_AMOUNT</code> is the minimum possible value for a quantity-type score, which is {@value} .
	 */
	static final double QUANTITY_MIN_AMOUNT = 0.0;

	/**
	 * Returns the {@linkplain Agent} of this {@linkplain Has}.
	 *
	 * @return the {@linkplain Agent} of this {@linkplain Has}.
	 */
	Agent getAgent();

	/**
	 * Returns the {@linkplain Attribute} of this {@linkplain Has}.
	 *
	 * @return the {@linkplain Attribute} of this {@linkplain Has}.
	 */
	Attribute getAttribute();

	/**
	 * Returns the <code>double</code> value of this {@linkplain Has}.
	 *
	 * @return the <code>double</code> value of this {@linkplain Has}.
	 */
	double getValue();

	/**
	 * Sets a new <code>double</code> value for this {@linkplain Has}.
	 *
	 * @param value
	 *            the <code>double</code> value to set.
	 */
	void setValue(double value);
}