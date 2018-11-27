package runtimemodels.chazm.api.id

import runtimemodels.chazm.api.Organization

import java.io.Serializable

/**
 * The [UniqueId] is an interface to uniquely identify elements within an [Organization].
 *
 * Implementations of [UniqueId] are not required to ensure that [UniqueId]s are globally unique across
 * [Organization]s due to the fact that [UniqueId]s are unique within an [Organization]; i.e.,
 * an [UniqueId] together with an [Organization] should be globally unique across [Organization]s.
 *
 * @param <T> the type of this [UniqueId].
 *
 * @author Christopher Zhong
 * @since 4.0
</T> */
@FunctionalInterface
interface UniqueId<T> : Serializable {
    /**
     * Returns the type of this [UniqueId].
     *
     * @return the type of this [UniqueId].
     */
    val type: Class<T>

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    override fun toString(): String
}
