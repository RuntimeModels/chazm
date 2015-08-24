package io.github.chriszhong.model.organization.event;

import io.github.chriszhong.message.M;
import io.github.chriszhong.model.organization.entity.Agent;
import io.github.chriszhong.model.organization.entity.Capability;
import io.github.chriszhong.model.organization.id.UniqueId;
import io.github.chriszhong.model.organization.relation.Possesses;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain PossessesEvent} class indicates that there is an update about a {@linkplain Possesses} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class PossessesEvent extends AbstractEvent {

	private static final long serialVersionUID = 1318353518805222229L;
	private final UniqueId<Agent> agentId;
	private final UniqueId<Capability> capabilityId;
	private final double score;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	PossessesEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Possesses possesses) {
		super(category);
		agentId = possesses.getAgent().getId();
		capabilityId = possesses.getCapability().getId();
		score = possesses.getScore();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents an {@linkplain Agent}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Agent> getAgentId() {
		return agentId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Capability}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Capability> getCapabilityId() {
		return capabilityId;
	}

	/**
	 * Returns a <code>double</code> score.
	 *
	 * @return a <code>double</code> score.
	 */
	public double getScore() {
		return score;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof PossessesEvent) {
			final PossessesEvent event = (PossessesEvent) object;
			return super.equals(event) && getAgentId().equals(event.getAgentId()) && getCapabilityId().equals(event.getCapabilityId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getCategory(), getAgentId(), getCapabilityId());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_2_IDS_AND_VALUE.get(super.toString(), getAgentId(), getCapabilityId(), getScore());
		}
		return toString;
	}

}
