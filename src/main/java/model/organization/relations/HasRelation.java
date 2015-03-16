/*
 * HasRelation.java
 *
 * Created on Oct 16, 2009
 *
 * See License.txt file the license agreement.
 */
package model.organization.relations;

import model.organization.entities.Agent;
import model.organization.entities.Attribute;
import model.organization.entities.Attribute.Type;

/**
 * The {@linkplain HasRelation} class is an implementation of the {@linkplain Has}.
 *
 * @author Christopher Zhong
 * @see Has
 * @since 5.0
 */
public class HasRelation implements Has {

	/**
	 * Internal <code>String</code> for the formatting of <code>HasRelation</code> class.
	 */
	private static final String STRING_FORMAT = "%s <-> %s: %f";

	/**
	 * The {@linkplain Agent} of this {@linkplain Has}.
	 */
	private final Agent agent;

	/**
	 * The {@linkplain Attribute} of this {@linkplain Has}.
	 */
	private final Attribute attribute;

	/**
	 * The <code>double</code> value of this {@linkplain Has}.
	 */
	private double value;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private int hashCode = 0;

	/**
	 * Constructs a new instance of {@linkplain Has}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of this {@linkplain Has}.
	 * @param attribute
	 *            the {@linkplain Attribute} of this {@linkplain Has}.
	 * @param value
	 *            the <code>double</code> value of this {@linkplain Has}.
	 */
	public HasRelation(final Agent agent, final Attribute attribute, final double value) {
		if (agent == null) {
			throw new IllegalArgumentException("Parameter (agent) cannot be null");
		}
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		this.agent = agent;
		this.attribute = attribute;
		setValue(value);
	}

	@Override
	public final Agent getAgent() {
		return agent;
	}

	@Override
	public final Attribute getAttribute() {
		return attribute;
	}

	@Override
	public final double getValue() {
		return value;
	}

	@Override
	public final void setValue(final double value) {
		final Type type = getAttribute().getType();
		switch (type) {
		case POSITIVE_QUALITY:
		case NEGATIVE_QUALITY:
			if (value < QUALITY_MIN_AMOUNT || value > QUALITY_MAX_AMOUNT) {
				throw new IllegalArgumentException(String.format("For atttribute type (%s), score (%f) must be between (%f) and (%f)", type, value,
						QUALITY_MIN_AMOUNT, QUALITY_MAX_AMOUNT));
			}
			break;
		case POSITIVE_QUANTITY:
		case NEGATIVE_QUANTITY:
			if (value < QUANTITY_MIN_AMOUNT) {
				throw new IllegalArgumentException(
						String.format("For atttribute type (%s), score (%f) must be at least (%f)", type, value, QUANTITY_MIN_AMOUNT));
			}
			break;
		case NEGATIVE_UNBOUNDED:
		case POSITIVE_UNBOUNDED:
			break;
		}
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Has) {
			final Has hasRelation = (Has) object;
			return getAgent().equals(hasRelation.getAgent()) && getAttribute().equals(hasRelation.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = getAgent().hashCode() << 16 | getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return String.format(STRING_FORMAT, getAgent().getId(), getAttribute().getId(), getValue());
	}

}
