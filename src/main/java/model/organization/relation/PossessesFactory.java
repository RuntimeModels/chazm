package model.organization.relation;

import model.organization.entity.Agent;
import model.organization.entity.Capability;

/**
 * The {@linkplain PossessesFactory} interface defines the API for constructing {@linkplain Possesses} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PossessesFactory {

	/**
	 * Constructs a {@linkplain Possesses}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of the {@linkplain Possesses}.
	 * @param capability
	 *            the {@linkplain Capability} of the {@linkplain Possesses}.
	 * @param score
	 *            the <code>double</code> score of the {@linkplain Possesses}.
	 * @return a {@linkplain Possesses}.
	 */
	Possesses buildPossesses(Agent agent, Capability capability, double score);

}