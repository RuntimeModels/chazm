package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.id.UniqueId
import kotlin.reflect.KClass

/**
 * The [AbstractId] class is an abstract implementation of [UniqueId].
 *
 * @param <T> the type of the [UniqueId]
 * @author Christopher Zhong
 * @since 7.0.0
</T> */
abstract class AbstractId<T : Any> protected constructor(
    override val type: KClass<T>
) : UniqueId<T> {
    override fun equals(other: Any?): Boolean {
        return if (other is UniqueId<*>) {
            type == other.type
        } else {
            false
        }
    }

    override fun hashCode(): Int = type.hashCode()

    override fun toString(): String = type.toString()

    companion object {
        private const val serialVersionUID = 2812867343219462118L
    }
}
