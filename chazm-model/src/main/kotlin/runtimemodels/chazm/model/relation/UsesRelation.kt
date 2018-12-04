package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Uses
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class UsesRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val pmf: Pmf
) : Uses {
    override fun equals(other: Any?): Boolean {
        if (other is UsesRelation) {
            return role == other.role && pmf == other.pmf
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(role, pmf)

    override fun toString(): String = M.RELATION[role.id, pmf.id]
}
