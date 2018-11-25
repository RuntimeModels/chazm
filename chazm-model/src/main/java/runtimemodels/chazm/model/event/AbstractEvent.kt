package runtimemodels.chazm.model.event

import runtimemodels.chazm.model.message.M
import java.io.Serializable

/**
 * The [AbstractEvent] class provides an easier way to implement events as they all have the type.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
abstract class AbstractEvent
/**
 * Constructs a new instance of [AbstractEvent].
 *
 * @param category the type of the update.
 */
protected constructor(
    /**
     * Returns the type of the update.
     *
     * @return the type.
     */
    val category: EventType
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (other is AbstractEvent) {
            return category == other.category
        }
        return false
    }

    override fun hashCode(): Int = category.hashCode()

    override fun toString(): String = M.EVENT[javaClass.simpleName, category]

    companion object {
        private const val serialVersionUID = 3392050291256215349L
    }

}
