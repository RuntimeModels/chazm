package runtimemodels.chazm.api.id

import runtimemodels.chazm.api.organization.Organization

import java.io.Serializable
import kotlin.reflect.KClass

/**
 * The [UniqueId] is an interface to uniquely identify elements within an [Organization].
 *
 * Implementations of [UniqueId] are not required to ensure that [UniqueId]s are globally unique across
 * [Organization]s due to the requirement that [UniqueId]s should be unique within an [Organization]; i.e.,
 * an [UniqueId] together with an [Organization] should be globally unique across [Organization]s.
 *
 * @param <T> the type for this [UniqueId].
 *
 * @author Christopher Zhong
 * @since 4.0
</T> */
@FunctionalInterface
interface UniqueId<T : Any> : Serializable {
    /**
     * The [KClass] for this [UniqueId].
     */
    val type: KClass<T>
}
