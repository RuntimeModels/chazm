package io.github.chriszhong.model.organization.relation;

import io.github.chriszhong.message.E;
import io.github.chriszhong.message.M;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.relation.Possesses;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

class PossessesRelation implements Possesses {

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
	public final Agent getAgent() {
		return agent;
	}

	@Override
	public final Capability getCapability() {
		return capability;
	}

	@Override
	public final double getScore() {
		return score;
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

}
