package runtimemodels.chazm.model.id

import com.google.inject.assistedinject.Assisted
import java.util.*
import javax.inject.Inject
import javax.validation.constraints.NotNull

@Deprecated("")
internal class ClassId<T> @Inject constructor(
    @Assisted type: Class<T>,
    @param:NotNull @param:Assisted val id: Class<*>
) : AbstractId<T>(type) {
    override fun equals(other: Any?): Boolean {
        if (other is ClassId<*>) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), id)

    override fun toString(): String = "${super.toString()}:$id"

    companion object {
        private const val serialVersionUID = 5751013993559212419L
    }
}
