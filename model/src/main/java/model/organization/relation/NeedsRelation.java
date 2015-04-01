package model.organization.relation;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import message.M;
import model.organization.entity.Attribute;
import model.organization.entity.Role;

import com.google.inject.assistedinject.Assisted;

class NeedsRelation implements Needs {

	private final Role role;
	private final Attribute attribute;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	NeedsRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Attribute attribute) {
		this.role = role;
		this.attribute = attribute;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Needs) {
			final Needs needs = (Needs) object;
			return getRole().equals(needs.getRole()) && getAttribute().equals(needs.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.RELATION.get(getRole().getId(), getAttribute().getId());
		}
		return toString;
	}

}
