package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

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

	@Inject
	RequiresEvent(@NotNull @Assisted final Requires requires, @NotNull @Assisted final EventCategory category) {
		super(category);
		checkNotNull(requires, "requires");
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

}
