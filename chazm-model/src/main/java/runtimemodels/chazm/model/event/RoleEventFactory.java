package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.entity.Role;

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
     * @param category the {@linkplain EventType}.
     * @param role     the {@linkplain Role}.
     * @return a {@linkplain RoleEvent}.
     */
    RoleEvent build(EventType category, Role role);

}
