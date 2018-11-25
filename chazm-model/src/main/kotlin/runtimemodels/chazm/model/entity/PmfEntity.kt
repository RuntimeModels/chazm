package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.UniqueId
import javax.inject.Inject

internal open class PmfEntity @Inject constructor(@Assisted id: UniqueId<Pmf>) : AbstractEntity<Pmf>(id), Pmf {
    override fun equals(other: Any?): Boolean {
        if (other is Pmf) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

}
