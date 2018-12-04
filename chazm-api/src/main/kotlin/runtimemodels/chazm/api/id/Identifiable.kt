package runtimemodels.chazm.api.id

import runtimemodels.chazm.api.organization.Organization

/**
 * The [Identifiable] interface is used to mark entities of an [Organization] that can be uniquely identified.
 *
 * @param <T> the type for the [UniqueId].
 * @param <U> the subtype of the [UniqueId].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface Identifiable<T : Any, U : UniqueId<T>> {
    /**
     * The `id`.
     */
    val id: U
}
