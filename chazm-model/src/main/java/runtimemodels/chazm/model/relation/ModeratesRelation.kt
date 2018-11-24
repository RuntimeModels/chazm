package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.relation.Moderates
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class ModeratesRelation @Inject constructor(
    @param:Assisted private val pmf: Pmf,
    @param:Assisted private val attribute: Attribute
) : Moderates {
    override fun equals(other: Any?): Boolean {
        if (other is Moderates) {
            return pmf == other.pmf && attribute == other.attribute
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(pmf, attribute)

    override fun toString(): String = M.RELATION.get(getPmf().id, getAttribute().id)!!

    override fun getPmf(): Pmf = pmf

    override fun getAttribute(): Attribute = attribute
}
