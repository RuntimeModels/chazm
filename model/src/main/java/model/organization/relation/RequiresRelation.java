package model.organization.relation;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Capability;
import model.organization.entity.Role;

import com.google.inject.assistedinject.Assisted;

class RequiresRelation implements Requires {

	private final Role role;
	private final Capability capability;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	RequiresRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Capability capability) {
		this.role = role;
		this.capability = capability;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Capability getCapability() {
		return capability;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Requires) {
			final Requires requires = (Requires) object;
			return getRole().equals(requires.getRole()) && getCapability().equals(requires.getCapability());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getCapability().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s <-> %s", getRole().getId(), getCapability().getId());
		}
		return toString;
	}

}
