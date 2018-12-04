package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.relation.Has
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class HasRelation @Inject constructor(
    @param:Assisted override val agent: Agent,
    @param:Assisted override val attribute: Attribute,
    @Assisted value: Double
) : Has {
    override var value: Double = value
        set(value) {
            val type = attribute.type
            when (type) {
                Attribute.Type.POSITIVE_QUALITY, Attribute.Type.NEGATIVE_QUALITY ->
                    if (value < QUALITY_MIN_AMOUNT || value > QUALITY_MAX_AMOUNT) {
                        throw IllegalArgumentException(E.VALUE_BETWEEN[type, value, QUALITY_MIN_AMOUNT, QUALITY_MAX_AMOUNT])
                    }
                Attribute.Type.POSITIVE_QUANTITY, Attribute.Type.NEGATIVE_QUANTITY ->
                    if (value < QUANTITY_MIN_AMOUNT) {
                        throw IllegalArgumentException(E.VALUE_AT_LEAST[type, value, QUANTITY_MIN_AMOUNT])
                    }
                else -> Unit
            }
            field = value
        }

    override fun equals(other: Any?): Boolean {
        if (other is Has) {
            return agent == other.agent && attribute == other.attribute
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(agent, attribute)

    override fun toString(): String = M.RELATION_WITH_VALUE[agent.id, attribute.id, value]

    companion object {
        /**
         * `QUALITY_MIN_AMOUNT` is the minimum possible value for a quality-type score, which is {@value} .
         */
        const val QUALITY_MIN_AMOUNT = 0.0
        /**
         * `QUALITY_MAX_AMOUNT` is the maximum possible value for a quality-type score, which is {@value} .
         */
        const val QUALITY_MAX_AMOUNT = 1.0
        /**
         * `QUANTITY_MIN_AMOUNT` is the minimum possible value for a quantity-type score, which is {@value} .
         */
        const val QUANTITY_MIN_AMOUNT = 0.0
    }
}
