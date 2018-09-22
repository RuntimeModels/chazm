package runtimemodels.chazm.model.relation;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.relation.Possesses;
import runtimemodels.chazm.message.E;
import runtimemodels.chazm.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class PossessesRelation implements Possesses {

    /**
     * <code>MIN_SCORE</code> is the minimum possible value for a score, which is {@value} .
     */
    static final double MIN_SCORE = 0.0;
    /**
     * <code>MAX_SCORE</code> is the maximum possible value for a score, which is {@value} .
     */
    static final double MAX_SCORE = 1.0;

    private final Agent agent;
    private final Capability capability;
    private double score;
    private transient Integer hashCode = null;

    @Inject
    PossessesRelation(@NotNull @Assisted final Agent agent, @NotNull @Assisted final Capability capability, @NotNull @Assisted final double score) {
        this.agent = agent;
        this.capability = capability;
        setScore(score);
    }

    @Override
    public final void setScore(final double score) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new IllegalArgumentException(E.SCORE_BETWEEN.get(score, MIN_SCORE, MAX_SCORE));
        }
        this.score = score;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Possesses) {
            final Possesses possesses = (Possesses) object;
            return getAgent().equals(possesses.getAgent()) && getCapability().equals(possesses.getCapability());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getAgent(), getCapability());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return M.RELATION_WITH_VALUE.get(getAgent().getId(), getCapability().getId(), getScore());
    }

    public Agent getAgent() {
        return this.agent;
    }

    public Capability getCapability() {
        return this.capability;
    }

    public double getScore() {
        return this.score;
    }
}
