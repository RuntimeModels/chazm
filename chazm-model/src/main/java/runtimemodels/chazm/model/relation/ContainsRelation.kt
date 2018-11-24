package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Contains
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class ContainsRelation @Inject constructor(
    @param:Assisted private val role: Role,
    @param:Assisted private val characteristic: Characteristic,
    @param:Assisted private var value: Double
) : Contains {
    override fun equals(other: Any?): Boolean {
        if (other is Contains) {
            return role == other.role && characteristic == other.characteristic
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(role, characteristic)

    override fun toString(): String = M.RELATION_WITH_VALUE.get(role.id, characteristic.id, value)!!

    override fun getRole(): Role = role

    override fun getCharacteristic(): Characteristic = characteristic

    override fun getValue(): Double = value

    override fun setValue(value: Double) {
        this.value = value
    }
}
