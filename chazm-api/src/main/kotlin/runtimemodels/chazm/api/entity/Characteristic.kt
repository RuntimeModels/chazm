package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Characteristic] interface defines a characteristic entity of an [Organization].
 *
 * @author Christopher Zhong
 * @since 6.0
 */
interface Characteristic : Identifiable<Characteristic> {
    /**
     * The [CharacteristicId] of this [Characteristic].
     */
    override val id: CharacteristicId
}
