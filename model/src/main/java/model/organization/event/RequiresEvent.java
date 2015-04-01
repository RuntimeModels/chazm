package model.organization.event;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import message.M;
import model.organization.entity.Capability;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Requires;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain RequiresEvent} class indicates that there is an update about a {@linkplain Requires} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class RequiresEvent extends AbstractEvent {

	private static final long serialVersionUID = -8416757195713372958L;
	private final UniqueId<Role> roleId;
	private final UniqueId<Capability> capabilityId;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	RequiresEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Requires requires) {
		super(category);
		roleId = requires.getRole().getId();
		capabilityId = requires.getCapability().getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Role}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Role> getRoleId() {
		return roleId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Capability}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Capability> getCapabilityId() {
		return capabilityId;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof RequiresEvent) {
			final RequiresEvent event = (RequiresEvent) object;
			return super.equals(event) && getRoleId().equals(event.getRoleId()) && getCapabilityId().equals(event.getCapabilityId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getRoleId().hashCode();
			hashCode = prime * hashCode + getCapabilityId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_2_IDS.get(super.toString(), getRoleId(), getCapabilityId());
		}
		return toString;
	}

}
