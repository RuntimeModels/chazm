package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class RequiresRelation @Inject constructor(
    @param:Assisted private val role: Role,
    @param:Assisted private val capability: Capability
) : Requires {
    override fun equals(other: Any?): Boolean {
        if (other is Requires) {
            return role == other.role && capability == other.capability
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(role, capability)

    override fun toString(): String = M.RELATION[role.id, capability.id]

    override fun getRole(): Role = role

    override fun getCapability(): Capability = capability
}
