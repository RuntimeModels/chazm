package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.CharacteristicId
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Contains
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [ContainsEvent] class indicates that there is an update about a [Contains] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class ContainsEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted contains: Contains
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: RoleId = contains.role.id
    /**
     * Returns a [UniqueId] that represents a [Characteristic].
     *
     * @return a [UniqueId].
     */
    val characteristicId: CharacteristicId = contains.characteristic.id
    /**
     * Returns a `double` value.
     *
     * @return a `double` value
     */
    val value: Double = contains.value

    override fun equals(other: Any?): Boolean {
        if (other is ContainsEvent) {
            return super.equals(other) && roleId == other.roleId && characteristicId == other.characteristicId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, roleId, characteristicId)

    override fun toString(): String = M.EVENT_WITH_2_IDS_AND_VALUE[super.toString(), roleId, characteristicId, value]

    companion object {
        private const val serialVersionUID = 87203168183879944L
    }

}
