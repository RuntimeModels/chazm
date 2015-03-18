package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;

/**
 * The {@linkplain RoleEvent} class indicates that there is an update about a {@linkplain Role} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class RoleEvent extends AbstractEvent {

	private static final long serialVersionUID = -5258695088891301883L;
	private final UniqueId<Role> id;

	/**
	 * Constructs a new instance of {@linkplain RoleEvent}.
	 *
	 * @param role
	 *            the {@linkplain Role}.
	 * @param category
	 *            the category of the update.
	 */
	public RoleEvent(final Role role, final UpdateCategory category) {
		super(category);
		checkNotNull(role, "role");
		id = role.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain SpecificationGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Role> getId() {
		return id;
	}

}
