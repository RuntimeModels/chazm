package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Capability;
import model.organization.id.UniqueId;

/**
 * The {@linkplain CapabilityEvent} class indicates that there is an update about a {@linkplain Capability} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class CapabilityEvent extends AbstractEvent {

	private static final long serialVersionUID = -8208865145150920878L;
	private final UniqueId<Capability> id;

	/**
	 * Constructs a new instance of {@linkplain CapabilityEvent}.
	 *
	 * @param capability
	 *            the {@linkplain Capability}.
	 * @param category
	 *            the category of the update.
	 */
	public CapabilityEvent(final Capability capability, final UpdateCategory category) {
		super(category);
		checkNotNull(capability, "capability");
		id = capability.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Capability}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Capability> getId() {
		return id;
	}

}
