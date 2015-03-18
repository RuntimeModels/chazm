package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Pmf;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Uses;

/**
 * The {@linkplain UsesEvent} class indicates that there is an update about an {@linkplain Uses} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class UsesEvent extends AbstractEvent {

	private static final long serialVersionUID = 5013118334434822084L;
	private final UniqueId<Role> roleId;
	private final UniqueId<Pmf> pmfId;

	/**
	 * Constructs a new instance of {@linkplain UsesEvent}.
	 *
	 * @param uses
	 *            the {@linkplain Uses}.
	 * @param category
	 *            the category of the update.
	 */
	public UsesEvent(final Uses uses, final UpdateCategory category) {
		super(category);
		checkNotNull(uses, "uses");
		roleId = uses.getRole().getId();
		pmfId = uses.getPmf().getId();
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
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Pmf}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Pmf> getPmfId() {
		return pmfId;
	}

}
