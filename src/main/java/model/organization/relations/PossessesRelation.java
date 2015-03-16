/*
 * PossessesRelation.java
 *
 * Created on Mar 3, 2005
 *
 * See License.txt file the license agreement.
 */
package model.organization.relations;

import message.M;
import model.organization.entities.Agent;
import model.organization.entities.Capability;

/**
 * The {@linkplain PossessesRelation} class is an implementation of the {@linkplain Possesses}.
 *
 * @author Christopher Zhong
 * @see Possesses
 * @since 1.0
 */
public class PossessesRelation implements Possesses {

	/**
	 * Internal <code>String</code> for the formatting of <code>PossessesRelation</code> class.
	 */
	private static final String STRING_FORMAT = "%s <-> %s: %f";

	/**
	 * The {@linkplain Agent} of this {@linkplain Possesses}.
	 */
	private final Agent agent;

	/**
	 * The {@linkplain Capability} of this {@linkplain Possesses}.
	 */
	private final Capability capability;

	/**
	 * The <code>double</code> score of this {@linkplain Possesses}.
	 */
	private double score;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private int hashCode = 0;

	/**
	 * Constructs a new instance of {@linkplain Possesses}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of this {@linkplain Possesses}.
	 * @param capability
	 *            the {@linkplain Capability} of this {@linkplain Possesses}.
	 * @param score
	 *            the <code>double</code> score of this {@linkplain Possesses}.
	 */
	public PossessesRelation(final Agent agent, final Capability capability, final double score) {
		if (agent == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "agent"));
		}
		if (capability == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "capability"));
		}
		this.agent = agent;
		this.capability = capability;
		setScore(score);
	}

	@Override
	public final Agent getAgent() {
		return agent;
	}

	@Override
	public final Capability getCapability() {
		return capability;
	}

	@Override
	public final double getScore() {
		return score;
	}

	@Override
	public final void setScore(final double score) {
		if (score < MIN_SCORE || score > MAX_SCORE) {
			throw new IllegalArgumentException(String.format("Score (%f) must be between (%f) and (%f)", score, MIN_SCORE, MAX_SCORE));
		}
		this.score = score;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Possesses) {
			final Possesses possesses = (Possesses) object;
			return getAgent().equals(possesses.getAgent()) && getCapability().equals(possesses.getCapability());
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
		return String.format(STRING_FORMAT, agent.getId(), capability.getId(), score);
	}

}
