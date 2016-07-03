package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.Role;

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
     * @param category the {@linkplain EventCategory}.
     * @param role     the {@linkplain Role}.
     * @return a {@linkplain RoleEvent}.
     */
    RoleEvent build(EventCategory category, Role role);

}
