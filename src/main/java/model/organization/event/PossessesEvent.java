package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Agent;
import model.organization.entity.Capability;
import model.organization.id.UniqueId;
import model.organization.relation.Possesses;

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

	/**
	 * Constructs a new instance of {@linkplain PossessesEvent}.
	 *
	 * @param possesses
	 *            the {@linkplain Possesses}.
	 * @param category
	 *            the category of the update.
	 */
	public PossessesEvent(final Possesses possesses, final UpdateCategory category) {
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
