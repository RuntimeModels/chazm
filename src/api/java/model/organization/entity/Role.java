package model.organization.entity;

import model.organization.Organization;
import model.organization.function.Goodness;
import model.organization.id.Identifiable;

/**
 * The {@linkplain Role} interface defines a role entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @see Goodness
 * @since 3.4
 */
public interface Role extends Identifiable<Role> {}