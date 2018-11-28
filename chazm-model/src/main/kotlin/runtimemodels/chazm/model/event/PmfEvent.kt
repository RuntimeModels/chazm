package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [PmfEvent] class indicates that there is an update about a [Pmf] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class PmfEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted pmf: Pmf
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Pmf].
     *
     * @return a [UniqueId].
     */
    val id: PmfId = pmf.id

    override fun equals(other: Any?): Boolean {
        if (other is PmfEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = 4714992287654266663L
    }

}
