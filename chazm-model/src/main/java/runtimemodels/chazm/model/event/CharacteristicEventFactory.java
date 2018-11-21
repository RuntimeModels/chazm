package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.entity.Characteristic;

/**
 * The {@linkplain CharacteristicEventFactory} interface defines the API for constructing {@linkplain CharacteristicEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface CharacteristicEventFactory {

    /**
     * Constructs an {@linkplain CharacteristicEvent}.
     *
     * @param category       the {@linkplain EventType}.
     * @param characteristic the {@linkplain Characteristic}.
     * @return a {@linkplain CharacteristicEvent}.
     */
    CharacteristicEvent build(EventType category, Characteristic characteristic);

}
