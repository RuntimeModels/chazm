package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [PolicyEvent] class indicates that there is an update about a [Policy] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class PolicyEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted policy: Policy
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Policy].
     *
     * @return a [UniqueId].
     */
    val id: UniqueId<Policy> = policy.id

    override fun equals(other: Any?): Boolean {
        if (other is PolicyEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = 1100497597698058236L
    }

}
