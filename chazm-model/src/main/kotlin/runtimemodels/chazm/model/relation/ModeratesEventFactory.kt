package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.relation.Moderates
import runtimemodels.chazm.model.event.EventType

/**
 * The [ModeratesEventFactory] interface defines the API for constructing [ModeratesEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface ModeratesEventFactory {
    /**
     * Constructs an [ModeratesEvent].
     *
     * @param category  the [EventType].
     * @param moderates the [Moderates].
     * @return a [ModeratesEvent].
     */
    fun build(category: EventType, moderates: Moderates): ModeratesEvent
}
