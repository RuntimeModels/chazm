package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.relation.Achieves

/**
 * The [AchievesEventFactory] interface defines the API for constructing [AchievesEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AchievesEventFactory {

    /**
     * Constructs an [AchievesEvent].
     *
     * @param category the [EventType].
     * @param achieves the [Achieves].
     * @return an [AchievesEvent].
     */
    fun build(category: EventType, achieves: Achieves): AchievesEvent

}
