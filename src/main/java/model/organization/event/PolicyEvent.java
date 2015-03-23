package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Policy;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain PolicyEvent} class indicates that there is an update about a {@linkplain Policy} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class PolicyEvent extends AbstractEvent {

	private static final long serialVersionUID = 1100497597698058236L;
	private final UniqueId<Policy> id;

	@Inject
	PolicyEvent(@NotNull @Assisted final Policy policy, @NotNull @Assisted final EventCategory category) {
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
