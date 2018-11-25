package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.UniqueId
import javax.inject.Inject

internal open class CapabilityEntity @Inject constructor(@Assisted id: UniqueId<Capability>) : AbstractEntity<Capability>(id), Capability {
    override fun equals(other: Any?): Boolean {
        if (other is Capability) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

}
