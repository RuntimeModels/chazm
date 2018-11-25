package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.entity.Attribute

/**
 * The [AttributeEventFactory] interface defines the API for constructing [AttributeEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AttributeEventFactory {

    /**
     * Constructs an [AttributeEvent].
     *
     * @param category  the [EventType].
     * @param attribute the [Attribute].
     * @return a [AttributeEvent].
     */
    fun build(category: EventType, attribute: Attribute): AttributeEvent

}
