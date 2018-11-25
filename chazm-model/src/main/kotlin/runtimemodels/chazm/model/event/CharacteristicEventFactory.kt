package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.entity.Characteristic

/**
 * The [CharacteristicEventFactory] interface defines the API for constructing [CharacteristicEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface CharacteristicEventFactory {

    /**
     * Constructs an [CharacteristicEvent].
     *
     * @param category       the [EventType].
     * @param characteristic the [Characteristic].
     * @return a [CharacteristicEvent].
     */
    fun build(category: EventType, characteristic: Characteristic): CharacteristicEvent

}
