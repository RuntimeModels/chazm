package io.github.chriszhong.model.organization.entity.manager;

import io.github.chriszhong.model.organization.entity.Attribute;
import io.github.chriszhong.model.organization.id.UniqueId;

import java.util.Collection;
import java.util.Set;

/**
 * The {@linkplain AttributeManager} interface defines the necessary APIs for managing {@linkplain Attribute}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface AttributeManager {
	/**
	 * Adds an {@linkplain Attribute} to this {@linkplain AttributeManager}.
	 *
	 * @param attribute
	 *            the {@linkplain Attribute} to add.
	 */
	void addAttribute(Attribute attribute);

	/**
	 * Adds a set of {@linkplain Attribute}s to this {@linkplain AttributeManager}.
	 *
	 * @param attributes
	 *            the set of {@linkplain Attribute} to add.
	 */
	void addAttributes(Collection<Attribute> attributes);

	/**
	 * Returns an {@linkplain Attribute} from this {@linkplain AttributeManager} .
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute} to retrieve.
	 * @return the {@linkplain Attribute} if it exists, <code>null</code> otherwise.
	 */
	Attribute getAttribute(UniqueId<Attribute> id);

	/**
	 * Returns a set of {@linkplain Attribute}s from this {@linkplain AttributeManager}.
	 *
	 * @return the set of {@linkplain Attribute}s.
	 */
	Set<Attribute> getAttributes();

	/**
	 * Removes an {@linkplain Attribute} from this {@linkplain AttributeManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute} to remove.
	 */
	void removeAttribute(UniqueId<Attribute> id);

	/**
	 * Removes a set of {@linkplain Attribute}s from this {@linkplain AttributeManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId}s that represents the {@linkplain Attribute}s to remove.
	 */
	void removeAttributes(Collection<UniqueId<Attribute>> ids);

	/**
	 * Removes all {@linkplain Attribute}s from this {@linkplain AttributeManager}.
	 */
	void removeAllAttributes();
}