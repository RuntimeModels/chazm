package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.model.event.EventType

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
