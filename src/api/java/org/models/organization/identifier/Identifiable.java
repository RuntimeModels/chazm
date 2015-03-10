package org.models.organization.identifier;

import org.models.organization.Organization;

/**
 * The {@linkplain Identifiable} interface is used to mark elements of an {@linkplain Organization} that can be uniquely identified.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface Identifiable {
	/**
	 * Returns the {@linkplain UniqueId}.
	 *
	 * @return the {@linkplain UniqueId}.
	 */
	UniqueId getId();
}
