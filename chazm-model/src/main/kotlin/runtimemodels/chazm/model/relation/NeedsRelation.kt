package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Needs
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class NeedsRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val attribute: Attribute
) : Needs {
    override fun equals(other: Any?): Boolean {
        if (other is Needs) {
            return role == other.role && attribute == other.attribute
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(role, attribute)

    override fun toString(): String = M.RELATION[role.id, attribute.id]
}
