/*
 * HasRelation.java
 *
 * Created on Oct 16, 2009
 *
 * See License.txt file the license agreement.
 */
package model.organization.relation;

import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Attribute.Type;

/**
 * The <code>HasRelation</code> class represents the has relation, where an
 * {@link Agent} has an {@link Attribute}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.5 $, $Date: 2010/06/28 21:21:08 $
 * @since 5.0
 */
public class HasRelation {

	/**
	 * <code>QUALITY_MIN_AMOUNT</code> is the minimum possible value for a
	 * quality-type score, which is {@value} .
	 */
	public static final double QUALITY_MIN_AMOUNT = 0.0;

	/**
	 * <code>QUALITY_MAX_AMOUNT</code> is the maximum possible value for a
	 * quality-type score, which is {@value} .
	 */
	public static final double QUALITY_MAX_AMOUNT = 1.0;

	/**
	 * <code>QUANTITY_MIN_AMOUNT</code> is the minimum possible value for a
	 * quantity-type score, which is {@value} .
	 */
	public static final double QUANTITY_MIN_AMOUNT = 0.0;

	/**
	 * Internal <code>String</code> for the formatting of
	 * <code>HasRelation</code> class.
	 */
	private static final String STRING_FORMAT = "%s <-> %s: %f";

	/**
	 * The <code>Agent</code> of this <code>HasRelation</code>.
	 */
	private final Agent<?> agent;

	/**
	 * The <code>Attribute</code> of this <code>HasRelation</code>.
	 */
	private final Attribute attribute;

	/**
	 * The value of this <code>HasRelation</code>.
	 */
	private double value;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private int hashCode = 0;

	/**
	 * Constructs a new instance of <code>HasRelation</code>.
	 * 
	 * @param agent
	 *            the <code>Agent</code> of this <code>HasRelation</code>.
	 * @param attribute
	 *            the <code>Attribute</code> of this <code>HasRelation</code>.
	 * @param value
	 *            the value of this <code>HasRelation</code>.
	 */
	public HasRelation(final Agent<?> agent, final Attribute attribute,
			final double value) {
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format(
					"Parameters (agent: %s, attribute: %s) cannot be null",
					agent, attribute));
		}
		this.agent = agent;
		this.attribute = attribute;
		setScore(value);
	}

	/**
	 * Returns the <code>Agent</code> of this <code>HasRelation</code>.
	 * 
	 * @return the <code>Agent</code> of this <code>HasRelation</code>.
	 */
	public final Agent<?> getAgent() {
		return agent;
	}

	/**
	 * Returns the <code>Attribute</code> of this <code>HasRelation</code>.
	 * 
	 * @return the <code>Attribute</code> of this <code>HasRelation</code>.
	 */
	public final Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Returns the score of this <code>HasRelation</code>.
	 * 
	 * @return the score of this <code>HasRelation</code>.
	 */
	public final double getValue() {
		return value;
	}

	/**
	 * Sets the new value for this <code>HasRelation</code>.
	 * 
	 * @param value
	 *            the new value for this <code>HasRelation</code>.
	 */
	public final void setScore(final double value) {
		final Type attributeType = getAttribute().getType();
		switch (attributeType) {
		case POSITIVE_QUALITY:
		case NEGATIVE_QUALITY:
			if (value < QUALITY_MIN_AMOUNT || value > QUALITY_MAX_AMOUNT) {
				throw new IllegalArgumentException(
						String.format(
								"For atttribute type (%s), score (%f) must be between (%f) and (%f)",
								attributeType, value, QUALITY_MIN_AMOUNT,
								QUALITY_MAX_AMOUNT));
			}
			break;
		case POSITIVE_QUANTITY:
		case NEGATIVE_QUANTITY:
			if (value < QUANTITY_MIN_AMOUNT) {
				throw new IllegalArgumentException(
						String.format(
								"For atttribute type (%s), score (%f) must be at least (%f)",
								attributeType, value, QUANTITY_MIN_AMOUNT));
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
		if (object instanceof HasRelation) {
			final HasRelation hasRelation = (HasRelation) object;
			return getAgent().equals(hasRelation.getAgent())
					&& getAttribute().equals(hasRelation.getAttribute());
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
		return String.format(STRING_FORMAT, getAgent().getIdentifier(),
				getAttribute().getIdentifier(), getValue());
	}

}
