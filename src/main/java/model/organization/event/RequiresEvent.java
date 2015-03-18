package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Capability;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Requires;

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

	/**
	 * Constructs a new instance of {@linkplain RequiresEvent}.
	 *
	 * @param requires
	 *            the {@linkplain Requires}.
	 * @param category
	 *            the category of the update.
	 */
	public RequiresEvent(final Requires requires, final UpdateCategory category) {
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
