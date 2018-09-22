package runtimemodels.chazm.model.entity;

import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.id.UniqueId;

/**
 * The {@linkplain CharacteristicFactory} interface defines the APIs for constructing {@linkplain Characteristic}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface CharacteristicFactory {

    /**
     * Constructs a {@linkplain Characteristic}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Characteristic}.
     * @return a {@linkplain Characteristic}.
     */
    Characteristic buildCharacteristic(UniqueId<Characteristic> id);

}
