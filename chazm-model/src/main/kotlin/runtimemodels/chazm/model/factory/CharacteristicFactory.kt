package runtimemodels.chazm.model.factory

import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [CharacteristicFactory] interface defines the APIs for constructing [Characteristic]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface CharacteristicFactory {

    /**
     * Constructs a [Characteristic].
     *
     * @param id the [UniqueId] that represents the [Characteristic].
     * @return a [Characteristic].
     */
    fun buildCharacteristic(id: CharacteristicId): Characteristic

}
