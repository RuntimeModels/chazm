package runtimemodels.chazm.model.id

import com.google.inject.assistedinject.Assisted
import java.util.*
import javax.inject.Inject

@Deprecated("")
internal class StringId<T> @Inject constructor(
    @Assisted type: Class<T>,
    @param:Assisted val id: String
) : AbstractId<T>(type) {
    override fun equals(other: Any?): Boolean {
        if (other is StringId<*>) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), id)

    override fun toString(): String = "${super.toString()}:$id"

    companion object {
        private const val serialVersionUID = 522905742372399827L
    }
}
