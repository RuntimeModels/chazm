package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class AttributeEntity @Inject constructor(
    @Assisted id: UniqueId<Attribute>,
    @param:Assisted override val type: Attribute.Type
) : AbstractEntity<Attribute>(id), Attribute {
    override fun equals(other: Any?): Boolean {
        if (other is Attribute) {
            return super.equals(other) && type == other.type
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), type)

    override fun toString(): String = M.ENTITY_1[super.toString(), type]
}
