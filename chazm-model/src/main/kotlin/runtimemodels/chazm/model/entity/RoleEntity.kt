package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.RoleId
import javax.inject.Inject

internal open class RoleEntity @Inject constructor(
    @Assisted override val id: RoleId
) : AbstractEntity<Role>(id), Role {

    override fun equals(other: Any?): Boolean {
        if (other is Role) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

}
