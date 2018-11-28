package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.id.CharacteristicId
import javax.inject.Inject

internal open class CharacteristicEntity @Inject constructor(
    @Assisted override val id: CharacteristicId
) : AbstractEntity<Characteristic>(id), Characteristic {

    override fun equals(other: Any?): Boolean {
        if (other is Characteristic) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

}
