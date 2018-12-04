package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Contains
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class ContainsRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val characteristic: Characteristic,
    @param:Assisted override var value: Double
) : Contains {
    override fun equals(other: Any?): Boolean {
        if (other is Contains) {
            return role == other.role && characteristic == other.characteristic
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(role, characteristic)

    override fun toString(): String = M.RELATION_WITH_VALUE[role.id, characteristic.id, value]
}
