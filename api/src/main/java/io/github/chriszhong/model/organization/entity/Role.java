package io.github.chriszhong.model.organization.entity;

import io.github.chriszhong.model.organization.Organization;
import io.github.chriszhong.model.organization.function.Goodness;
import io.github.chriszhong.model.organization.id.Identifiable;

/**
 * The {@linkplain Role} interface defines a role entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @see Goodness
 * @since 3.4
 */
public interface Role extends Identifiable<Role> {}