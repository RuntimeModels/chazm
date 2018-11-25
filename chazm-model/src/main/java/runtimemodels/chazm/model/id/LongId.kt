package runtimemodels.chazm.model.id

import com.google.inject.assistedinject.Assisted
import java.util.*
import javax.inject.Inject

@Deprecated("")
internal class LongId<T> @Inject constructor(
    @Assisted type: Class<T>,
    @param:Assisted val id: Long
) : AbstractId<T>(type) {
    override fun equals(other: Any?): Boolean {
        if (other is LongId<*>) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), id)

    override fun toString(): String = "${super.toString()}:$id"

    companion object {
        private const val serialVersionUID = 8542765061773217208L
    }
}
