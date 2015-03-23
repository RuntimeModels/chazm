package model.organization.event;

import model.organization.entity.Role;

/**
 * The {@linkplain RoleEventFactory} interface defines the API for constructing {@linkplain RoleEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface RoleEventFactory {

	/**
	 * Constructs an {@linkplain RoleEvent}.
	 *
	 * @param role
	 *            the {@linkplain Role}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain RoleEvent}.
	 */
	RoleEvent build(Role role, EventCategory category);

}