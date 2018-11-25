package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.UniqueId
import javax.inject.Inject

internal open class RoleEntity @Inject
constructor(@Assisted id: UniqueId<Role>) : AbstractEntity<Role>(id), Role {

    override fun equals(other: Any?): Boolean {
        if (other is Role) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

}
