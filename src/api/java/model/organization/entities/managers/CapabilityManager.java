package model.organization.entities.managers;

import java.util.Collection;
import java.util.Set;

import model.organization.entities.Capability;
import model.organization.id.UniqueId;

/**
 * The {@linkplain CapabilityManager} interface defines the necessary APIs for managing {@linkplain Capability}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface CapabilityManager {
	/**
	 * Adds a {@linkplain Capability} to this {@linkplain CapabilityManager}.
	 *
	 * @param capability
	 *            the {@linkplain Capability} to add.
	 */
	void addCapability(Capability capability);

	/**
	 * Adds a set of {@linkplain Capability}s to this {@linkplain CapabilityManager}.
	 *
	 * @param capabilities
	 *            the set of {@linkplain Capability}s to add.
	 */
	void addCapabilities(Collection<Capability> capabilities);

	/**
	 * Returns a {@linkplain Capability} from this {@linkplain CapabilityManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability} to retrieve.
	 * @return the {@linkplain Capability} if it exists, <code>null</code> otherwise.
	 */
	Capability getCapability(UniqueId<Capability> id);

	/**
	 * Returns a set of {@linkplain Capability}s from this {@linkplain CapabilityManager}.
	 *
	 * @return the set of {@linkplain Capability}.
	 */
	Set<Capability> getCapabilities();

	/**
	 * Removes a {@linkplain Capability} from this {@linkplain CapabilityManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability} to remove.
	 */
	void removeCapability(UniqueId<Capability> id);

	/**
	 * Removes a set of {@linkplain Capability}s from this {@linkplain CapabilityManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId}s that represents the {@linkplain Capability}s to remove.
	 */
	void removeCapabilities(Collection<UniqueId<Capability>> ids);

	/**
	 * Removes all {@linkplain Capability}s from this {@linkplain CapabilityManager}.
	 */
	void removeAllCapabilities();
}