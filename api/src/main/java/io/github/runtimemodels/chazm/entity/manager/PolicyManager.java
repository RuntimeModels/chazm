package io.github.runtimemodels.chazm.entity.manager;

import io.github.runtimemodels.chazm.entity.Policy;
import io.github.runtimemodels.chazm.id.UniqueId;

import java.util.Collection;
import java.util.Set;

/**
 * The {@linkplain PolicyManager} interface defines the necessary APIs for managing {@linkplain Policy}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface PolicyManager {
	/**
	 * Adds a {@linkplain Policy} to this {@linkplain PolicyManager}.
	 *
	 * @param policy
	 *            the {@linkplain Policy} to add.
	 */
	void addPolicy(Policy policy);

	/**
	 * Adds a set of {@linkplain Policy}s to this {@linkplain PolicyManager}.
	 *
	 * @param policies
	 *            the set of {@linkplain Policy} to add.
	 */
	void addPolicies(Collection<Policy> policies);

	/**
	 * Returns a {@linkplain Policy} from this {@linkplain PolicyManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Policy} to retrieve.
	 * @return the {@linkplain Policy} if it exists, <code>null</code> otherwise.
	 */
	Policy getPolicy(UniqueId<Policy> id);

	/**
	 * Returns a set of {@linkplain Policy} from this {@linkplain PolicyManager}.
	 *
	 * @return the set of {@linkplain Policy}.
	 */
	Set<Policy> getPolicies();

	/**
	 * Removes a {@linkplain Policy} from this {@linkplain PolicyManager}.
	 *
	 * @param policyId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Policy} to remove.
	 */
	void removePolicy(UniqueId<Policy> policyId);

	/**
	 * Removes a set of {@linkplain Policy}s from this {@linkplain PolicyManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId}s that represents the {@linkplain Policy}s to remove.
	 */
	void removePolicies(Collection<UniqueId<Policy>> ids);

	/**
	 * Removes all {@linkplain Policy}s from this {@linkplain PolicyManager}.
	 */
	void removeAllPolicies();
}