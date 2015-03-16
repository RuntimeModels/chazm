package model.organization.relations.managers;

import java.util.Set;

import model.organization.entities.Capability;
import model.organization.entities.Role;
import model.organization.id.UniqueId;
import model.organization.relations.Requires;

/**
 * The {@linkplain RequiresManager} interface defines the APIs for managing the {@linkplain Requires}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface RequiresManager {
	/**
	 * Creates a requires relation between a {@linkplain Role} and a {@linkplain Capability} in this {@linkplain RequiresManager}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 */
	void addRequires(UniqueId<Role> roleId, UniqueId<Capability> capabilityId);

	/**
	 * Returns a set of {@linkplain Capability}s that is required by a {@linkplain Role} in this {@linkplain RequiresManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @return the set of {@linkplain Capability}s.
	 */
	Set<Capability> getRequires(UniqueId<Role> id);

	/**
	 * Returns a set of {@linkplain Role}s that requires a {@linkplain Capability} in this {@linkplain RequiresManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 * @return a set of {@linkplain Role}s.
	 */
	Set<Role> getRequiredBy(UniqueId<Capability> id);

	/**
	 * Removes a requires relation between a {@linkplain Role} and a {@linkplain Capability} from this {@linkplain RequiresManager}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 */
	void removeRequires(UniqueId<Role> roleId, UniqueId<Capability> capabilityId);

	/**
	 * Removes all requires relations from this {@linkplain RequiresManager}.
	 */
	void removeAllRequires();
}