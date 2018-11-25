package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.entity.Role

/**
 * The [RoleEventFactory] interface defines the API for constructing [RoleEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface RoleEventFactory {

    /**
     * Constructs an [RoleEvent].
     *
     * @param category the [EventType].
     * @param role     the [Role].
     * @return a [RoleEvent].
     */
    fun build(category: EventType, role: Role): RoleEvent

}
