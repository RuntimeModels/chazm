package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.id.UniqueId

/**
 * The [IdFactory] interface defines the APIs for constructing [UniqueId]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface IdFactory {

    /**
     * Constructs an [UniqueId].
     *
     * @param <T> the type that this [UniqueId] will be associated.
     * @param <U> the type of the identifier for this [UniqueId].
     * @param clazz the type of the [UniqueId].
     * @param id    the [Class] id for the [UniqueId].
     * @return an [UniqueId].
    </U></T> */
    fun <T, U> build(clazz: Class<T>, id: Class<U>): UniqueId<T>

    /**
     * Constructs an [UniqueId].
     *
     * @param <T> the type that this [UniqueId] will be associated.
     * @param clazz the type of the [UniqueId].
     * @param id    the `long` id for the [UniqueId].
     * @return an [UniqueId].
    </T> */
    fun <T> build(clazz: Class<T>, id: Long?): UniqueId<T>

    /**
     * Constructs an [UniqueId].
     *
     * @param <T> the type that this [UniqueId] will be associated.
     * @param clazz the type of the [UniqueId].
     * @param id    the [String] id for the [UniqueId].
     * @return an [UniqueId].
    </T> */
    fun <T> build(clazz: Class<T>, id: String): UniqueId<T>

}
