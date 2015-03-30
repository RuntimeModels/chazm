package model.organization.event;

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
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	CapabilityEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Capability capability) {
		super(category);
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

	@Override
	public boolean equals(final Object object) {
		if (object instanceof CapabilityEvent) {
			final CapabilityEvent event = (CapabilityEvent) object;
			return super.equals(event) && getId().equals(event.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s, %s)", getClass().getSimpleName(), getCategory(), getId());
		}
		return toString;
	}

}
