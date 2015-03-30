package model.organization.event;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Pmf;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Uses;

import com.google.inject.assistedinject.Assisted;

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
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	UsesEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Uses uses) {
		super(category);
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

	@Override
	public boolean equals(final Object object) {
		if (object instanceof UsesEvent) {
			final UsesEvent event = (UsesEvent) object;
			return super.equals(event) && getRoleId().equals(event.getRoleId()) && getPmfId().equals(event.getPmfId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getRoleId().hashCode();
			hashCode = prime * hashCode + getPmfId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s, %s, %s)", getClass().getSimpleName(), getCategory(), getRoleId(), getPmfId());
		}
		return toString;
	}

}
