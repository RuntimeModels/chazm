package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Agent;
import model.organization.entity.Capability;
import model.organization.id.UniqueId;
import model.organization.relation.Possesses;

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

	@Inject
	PossessesEvent(@NotNull @Assisted final Possesses possesses, @NotNull @Assisted final EventCategory category) {
		super(category);
		checkNotNull(possesses, "possesses");
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

}
