package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Attribute;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Needs;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain NeedsEvent} class indicates that there is an update about a {@linkplain Needs} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class NeedsEvent extends AbstractEvent {

	private static final long serialVersionUID = -2368504360543452399L;
	private final UniqueId<Role> roleId;
	private final UniqueId<Attribute> attributeId;

	@Inject
	NeedsEvent(@NotNull @Assisted final Needs needs, @NotNull @Assisted final EventCategory category) {
		super(category);
		checkNotNull(needs, "needs");
		roleId = needs.getRole().getId();
		attributeId = needs.getAttribute().getId();
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
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Attribute}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Attribute> getAttributeId() {
		return attributeId;
	}

}
