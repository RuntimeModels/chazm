package model.organization.relations;

import model.organization.Organization;
import model.organization.entities.Capability;
import model.organization.entities.Role;

/**
 * The {@linkplain Requires} interface defines the requires relation, where a {@linkplain Role} requires a {@linkplain Capability}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Requires {
	/**
	 * Returns the {@linkplain Role} of this {@linkplain Requires}.
	 *
	 * @return the {@linkplain Role} of this {@linkplain Requires}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain Capability} of this {@linkplain Requires}.
	 *
	 * @return the {@linkplain Capability} of this {@linkplain Requires}.
	 */
	Capability getCapability();
}