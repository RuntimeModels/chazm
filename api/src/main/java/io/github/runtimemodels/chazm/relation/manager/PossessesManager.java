package io.github.runtimemodels.chazm.relation.manager;

import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.chazm.relation.Possesses;

import java.util.Set;

/**
 * The {@linkplain PossessesManager} interface defines the APIs for managing {@linkplain Possesses}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface PossessesManager {
	/**
	 * Creates a possesses relation between an {@linkplain Agent} and a {@linkplain Capability} in this {@linkplain PossessesManager}.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent}.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 * @param score
	 *            the <code>double</code> score of the possesses relation.
	 */
	void addPossesses(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId, double score);

	/**
	 * Returns a set of {@linkplain Capability}s that is possessed by an {@linkplain Agent} in this {@linkplain PossessesManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent}.
	 * @return a set of {@linkplain Capability}s.
	 */
	Set<Capability> getPossesses(UniqueId<Agent> id);

	/**
	 * Returns a set of {@linkplain Agent}s that possesses a {@linkplain Capability} in this {@linkplain PossessesManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 * @return a set of {@linkplain Agent}s.
	 */
	Set<Agent> getPossessedBy(UniqueId<Capability> id);

	/**
	 * Returns the <code>double</code> score of the possesses relation between an {@linkplain Agent} and a {@linkplain Capability} in this
	 * {@linkplain PossessesManager}.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent}.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 * @return the <code>double</code> score.
	 */
	double getPossessesScore(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId);

	/**
	 * Sets a new <code>double</code> score for the possesses relation between an {@linkplain Agent} and a {@linkplain Capability} in this
	 * {@linkplain PossessesManager}.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent}.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 * @param score
	 *            the new <code>double</code> score.
	 */
	void setPossessesScore(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId, double score);

	/**
	 * Removes a possesses relation between an {@linkplain Agent} and a {@linkplain Capability} from this {@linkplain PossessesManager}.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent}.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 */
	void removePossesses(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId);

	/**
	 * Removes all possesses relations from this {@linkplain PossessesManager}.
	 */
	void removeAllPossesses();
}