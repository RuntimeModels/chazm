package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.CharacteristicId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [CharacteristicEvent] class indicates that there is an update about a [Characteristic] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class CharacteristicEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted characteristic: Characteristic
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Characteristic].
     *
     * @return a [UniqueId].
     */
    val id: CharacteristicId = characteristic.id
    @Transient
    private var hashCode: Int? = null
    @Transient
    private var toString: String? = null

    override fun equals(other: Any?): Boolean {
        if (other is CharacteristicEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = -7464874692386039815L
    }

}
