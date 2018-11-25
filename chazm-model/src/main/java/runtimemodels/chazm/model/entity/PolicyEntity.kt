package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.id.UniqueId

import javax.inject.Inject

internal open class PolicyEntity @Inject constructor(@Assisted id: UniqueId<Policy>) : AbstractEntity<Policy>(id), Policy {

    override fun equals(other: Any?): Boolean {
        if (other is Policy) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

}
