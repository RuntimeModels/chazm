package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain RoleEvent} class indicates that there is an update about a {@linkplain Role} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class RoleEvent extends AbstractEvent {

	private static final long serialVersionUID = -5258695088891301883L;
	private final UniqueId<Role> id;

	@Inject
	RoleEvent(@NotNull @Assisted final Role role, @NotNull @Assisted final EventCategory category) {
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
