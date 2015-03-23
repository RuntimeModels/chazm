package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.validation.constraints.NotNull;

import model.organization.entity.Policy;
import model.organization.id.UniqueId;

/**
 * The {@linkplain PolicyEvent} class indicates that there is an update about a {@linkplain Policy} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class PolicyEvent extends AbstractEvent {

	private static final long serialVersionUID = 1100497597698058236L;
	private final UniqueId<Policy> id;

	/**
	 * Constructs a new instance of {@linkplain PolicyEvent}.
	 *
	 * @param policy
	 *            the {@linkplain Policy}.
	 * @param category
	 *            the category of the update.
	 */
	public PolicyEvent(@NotNull final Policy policy, @NotNull final UpdateCategory category) {
		super(category);
		checkNotNull(policy, "policy");
		id = policy.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Policy}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Policy> getId() {
		return id;
	}

}
