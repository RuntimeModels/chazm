package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.relation.Possesses
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class PossessesRelation @Inject constructor(
    @param:Assisted override val agent: Agent,
    @param:Assisted override val capability: Capability,
    @Assisted score: Double
) : Possesses {
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
