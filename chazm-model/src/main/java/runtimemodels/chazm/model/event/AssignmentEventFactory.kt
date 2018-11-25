package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.relation.Assignment

/**
 * The [AssignmentEventFactory] interface defines the API for constructing [AssignmentEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AssignmentEventFactory {

    /**
     * Constructs an [AssignmentEvent].
     *
     * @param category   the [EventType].
     * @param assignment the [Assignment].
     * @return a [AssignmentEvent].
     */
    fun build(category: EventType, assignment: Assignment): AssignmentEvent

}
