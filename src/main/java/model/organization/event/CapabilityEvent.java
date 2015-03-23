package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Capability;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain CapabilityEvent} class indicates that there is an update about a {@linkplain Capability} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class CapabilityEvent extends AbstractEvent {

	private static final long serialVersionUID = -8208865145150920878L;
	private final UniqueId<Capability> id;

	@Inject
	CapabilityEvent(@NotNull @Assisted final Capability capability, @NotNull @Assisted final EventCategory category) {
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
