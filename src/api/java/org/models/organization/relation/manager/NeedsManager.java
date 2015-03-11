package org.models.organization.relation.manager;

import java.util.Set;

import org.models.organization.entity.Attribute;
import org.models.organization.entity.Role;
import org.models.organization.id.UniqueId;
import org.models.organization.relation.Needs;

/**
 * The {@linkplain NeedsManager} interface defines the API for managing {@linkplain Needs}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface NeedsManager {
	/**
	 * Creates a needs relation between a {@linkplain Role} and an {@linkplain Attribute} in this {@linkplain NeedsManager}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 */
	void addNeeds(UniqueId<Role> roleId, UniqueId<Attribute> attributeId);

	/**
	 * Returns a set of {@linkplain Attribute}s that is needed by a {@linkplain Role} in this {@linkplain NeedsManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @return a set of {@linkplain Attribute}s.
	 */
	Set<Attribute> getNeeds(UniqueId<Role> id);

	/**
	 * Returns a set of {@linkplain Role}s that needs an {@linkplain Attribute} in this {@linkplain NeedsManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 * @return a set of {@linkplain Role}s.
	 */
	Set<Role> getNeededBy(UniqueId<Attribute> id);

	/**
	 * Removes a needs relation between a {@linkplain Role} and an {@linkplain Attribute} from this {@linkplain NeedsManager}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 */
	void removeNeeds(UniqueId<Role> roleId, UniqueId<Attribute> attributeId);

	/**
	 * Removes all needs relations from this {@linkplain NeedsManager}.
	 */
	void removeAllNeeds();

}