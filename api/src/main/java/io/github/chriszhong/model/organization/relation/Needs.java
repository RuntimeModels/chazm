package io.github.chriszhong.model.organization.relation;

import io.github.chriszhong.model.organization.Organization;
import io.github.chriszhong.model.organization.entity.Attribute;
import io.github.chriszhong.model.organization.entity.Role;

/**
 * The {@linkplain Needs} interface defines the needs relation, where a {@linkplain Role} needs an {@linkplain Attribute}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Needs {
	/**
	 * Returns the {@linkplain Role} of this {@linkplain Needs}.
	 *
	 * @return the {@linkplain Role} of this {@linkplain Needs}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain Attribute} of this {@linkplain Needs}.
	 *
	 * @return the {@linkplain Attribute} of this {@linkplain Needs}.
	 */
	Attribute getAttribute();
}