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
    @param:Assisted private val agent: Agent,
    @param:Assisted private val capability: Capability,
    @param:Assisted private var score: Double
) : Possesses {
    init {
        setScore(score)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Possesses) {
            return agent == other.agent && capability == other.capability
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(agent, capability)

    override fun toString(): String = M.RELATION_WITH_VALUE[agent.id, capability.id, score]

    override fun getAgent(): Agent = agent

    override fun getCapability(): Capability = capability

    override fun getScore(): Double = score

    override fun setScore(score: Double) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw IllegalArgumentException(E.SCORE_BETWEEN[score, MIN_SCORE, MAX_SCORE])
        }
        this.score = score
    }

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
