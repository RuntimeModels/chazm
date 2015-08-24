package io.github.chriszhong.model.organization.entity;

import io.github.chriszhong.model.organization.id.UniqueId;

/**
 * The {@linkplain RoleFactory} interface defines the APIs for constructing {@linkplain Role}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface RoleFactory {

	/**
	 * Constructs a {@linkplain Role}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @return a {@linkplain Role}.
	 */
	Role buildRole(UniqueId<Role> id);

}