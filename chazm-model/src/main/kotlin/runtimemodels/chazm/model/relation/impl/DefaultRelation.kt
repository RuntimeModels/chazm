package runtimemodels.chazm.model.relation.impl

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal sealed class DefaultRelation

internal data class AchievesRelation @Inject constructor(
    override val role: Role,
    override val goal: SpecificationGoal
) : Achieves, DefaultRelation()

internal data class AssignmentRelation @Inject constructor(
    override val agent: Agent,
    override val role: Role,
    override val goal: InstanceGoal
) : Assignment, DefaultRelation()

// TODO change to a data class without using value in the equals
internal open class ContainsRelation @Inject constructor(
    override val role: Role,
    override val characteristic: Characteristic,
    override var value: Double
) : Contains, DefaultRelation() {
    override fun equals(other: Any?): Boolean {
        if (other is Contains) {
            return role == other.role && characteristic == other.characteristic
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(role, characteristic)

    override fun toString(): String = M.RELATION_WITH_VALUE[role.id, characteristic.id, value]
}

// TODO change to a data class without using value in the equals
internal open class HasRelation @Inject constructor(
    override val agent: Agent,
    override val attribute: Attribute,
    value: Double
) : Has, DefaultRelation() {
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

internal data class ModeratesRelation @Inject constructor(
    override val pmf: Pmf,
    override val attribute: Attribute
) : Moderates, DefaultRelation()

internal data class NeedsRelation @Inject constructor(
    override val role: Role,
    override val attribute: Attribute
) : Needs, DefaultRelation()

// TODO change to a data class without using value in the equals
internal open class PossessesRelation @Inject constructor(
    override val agent: Agent,
    override val capability: Capability,
    score: Double
) : Possesses, DefaultRelation() {
    override var score: Double = score
        set(value) {
            if (score < MIN_SCORE || score > MAX_SCORE) {
                throw IllegalArgumentException(E.SCORE_BETWEEN[value, MIN_SCORE, MAX_SCORE])
            }
            field = value
        }

    override fun equals(other: Any?): Boolean {
        if (other is Possesses) {
            return agent == other.agent && capability == other.capability
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(agent, capability)

    override fun toString(): String = M.RELATION_WITH_VALUE[agent.id, capability.id, score]

    companion object {
        /**
         * `MIN_SCORE` is the minimum possible value for a score, which is {@value} .
         */
        const val MIN_SCORE = 0.0
        /**
         * `MAX_SCORE` is the maximum possible value for a score, which is {@value} .
         */
        const val MAX_SCORE = 1.0
    }
}

internal data class RequiresRelation @Inject constructor(
    override val role: Role,
    override val capability: Capability
) : Requires, DefaultRelation()

internal data class UsesRelation @Inject constructor(
    override val role: Role,
    override val pmf: Pmf
) : Uses, DefaultRelation()
