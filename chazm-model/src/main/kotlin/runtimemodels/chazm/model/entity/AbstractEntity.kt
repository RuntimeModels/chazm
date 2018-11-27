package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.M

/**
 * The [AbstractEntity] provides an abstract class for entities.
 *
 * @param <T> the type of the [UniqueId].
 * @author Christopher Zhong
 * @since 7.0.0
</T> */
internal abstract class AbstractEntity<T> constructor(
    override val id: UniqueId<T>
) : Identifiable<T> {
    override fun equals(other: Any?): Boolean {
        if (other is Identifiable<*>) {
            return id == other.id
        }
        return false
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = M.ENTITY_0[javaClass.simpleName, id]
}
