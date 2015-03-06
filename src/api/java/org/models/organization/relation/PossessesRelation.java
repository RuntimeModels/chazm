/*
 * PossessesRelation.java
 *
 * Created on Mar 3, 2005
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.entity.Agent;
import org.models.organization.entity.Capability;

/**
 * Represents the possesses relation, where an {@link Agent} possesses a {@link Capability}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.5.2.1 $, $Date: 2010/06/24 19:26:06 $
 * @see Agent
 * @see Capability
 * @since 1.0
 */
public class PossessesRelation {

	/**
	 * <code>MIN_SCORE</code> is the minimum possible value for a score, which is {@value} .
	 */
	public static final double MIN_SCORE = 0.0;

	/**
	 * <code>MAX_SCORE</code> is the maximum possible value for a score, which is {@value} .
	 */
	public static final double MAX_SCORE = 1.0;

	/**
	 * Internal <code>String</code> for the formatting of <code>PossessesRelation</code> class.
	 */
	private static final String STRING_FORMAT = "%s <-> %s: %f";

	/**
	 * The <code>Agent</code> of this <code>PossessesRelation</code> relation.
	 */
	private final Agent agent;

	/**
	 * The <code>Capability</code> of this <code>PossessesRelation</code> relation.
	 */
	private final Capability capability;

	/**
	 * The score of this <code>PossessesRelation</code> relation.
	 */
	private double score;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private int hashCode = 0;

	/**
	 * Constructs a new instance of the <code>PossessesRelation</code>.
	 *
	 * @param agent
	 *            the <code>Agent</code> of this <code>PossessesRelation</code>.
	 * @param capability
	 *            the <code>Capability</code> of this <code>PossessesRelation</code>.
	 * @param score
	 *            the score of this <code>PossessesRelation</code>.
	 */
	public PossessesRelation(final Agent agent, final Capability capability, final double score) {
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format("Parameters (agent: %s, capability: %s) cannot be null", agent, capability));
		}
		this.agent = agent;
		this.capability = capability;
		setScore(score);
	}

	/**
	 * Returns the <code>Agent</code> of this <code>PossessesRelation</code>.
	 *
	 * @return the <code>Agent</code> of this <code>PossessesRelation</code>.
	 */
	public final Agent getAgent() {
		return agent;
	}

	/**
	 * Returns the <code>Capability</code> of this <code>PossessesRelation</code>.
	 *
	 * @return the <code>Capability</code> of this <code>PossessesRelation</code>.
	 */
	public final Capability getCapability() {
		return capability;
	}

	/**
	 * Returns the score of this <code>PossessesRelation</code>.
	 *
	 * @return the score of this <code>PossessesRelation</code>.
	 */
	public final double getScore() {
		return score;
	}

	/**
	 * Changes the score of this <code>PossessesRelation</code> to the given score.
	 *
	 * @param score
	 *            the new score of this <code>PossessesRelation</code>.
	 */
	public final void setScore(final double score) {
		if (score < MIN_SCORE || score > MAX_SCORE) {
			throw new IllegalArgumentException(String.format("Score (%f) must be between (%f) and (%f)", score, MIN_SCORE, MAX_SCORE));
		}
		this.score = score;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof PossessesRelation) {
			final PossessesRelation possessesRelation = (PossessesRelation) object;
			return getAgent().equals(possessesRelation.getAgent()) && getCapability().equals(possessesRelation.getCapability());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = getAgent().hashCode() << 16 | getCapability().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return String.format(STRING_FORMAT, agent.getIdentifier(), capability.getIdentifier(), score);
	}

}
