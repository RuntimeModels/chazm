package model.organization.entity.manager;

import java.util.Collection;
import java.util.Set;

import model.organization.entity.Agent;
import model.organization.id.UniqueId;

/**
 * The {@linkplain AgentManager} interface defines the necessary APIs for managing {@linkplain Agent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface AgentManager {
	/**
	 * Adds a {@linkplain Agent} to this {@linkplain AgentManager}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} to add.
	 */
	void addAgent(Agent agent);

	/**
	 * Adds a set of {@linkplain Agent}s to this {@linkplain AgentManager} .
	 *
	 * @param agents
	 *            the set of {@linkplain Agent}s to add.
	 */
	void addAgents(Collection<Agent> agents);

	/**
	 * Returns an {@linkplain Agent} from this {@linkplain AgentManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent} to retrieve.
	 * @return the {@linkplain Agent} if it exists, <code>null</code> otherwise.
	 */
	Agent getAgent(UniqueId<Agent> id);

	/**
	 * Returns a set of {@linkplain Agent}s from this {@linkplain AgentManager}.
	 *
	 * @return the set of {@linkplain Agent}.
	 */
	Set<Agent> getAgents();

	/**
	 * Removes an {@linkplain Agent} from this {@linkplain AgentManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent} to remove.
	 */
	void removeAgent(UniqueId<Agent> id);

	/**
	 * Removes a set of {@linkplain Agent}s from this {@linkplain AgentManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId}s that represents the {@linkplain Agent}s to remove.
	 */
	void removeAgents(Collection<UniqueId<Agent>> ids);

	/**
	 * Removes all {@linkplain Agent}s from this {@linkplain AgentManager}.
	 */
	void removeAllAgents();
}