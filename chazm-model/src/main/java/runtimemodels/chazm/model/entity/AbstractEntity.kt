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
abstract class AbstractEntity<T> @java.beans.ConstructorProperties("id") constructor(
    private val id: UniqueId<T>
) : Identifiable<T> {
    override fun equals(other: Any?): Boolean {
        if (other is Identifiable<*>) {
            return getId() == other.id
        }
        return false
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = M.ENTITY_0[javaClass.simpleName, id]

    override fun getId(): UniqueId<T> = id
}
