package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.relation.Uses;
import io.github.runtimemodels.message.M;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

class UsesRelation implements Uses {

	private final Role role;
	private final Pmf pmf;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	UsesRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Pmf pmf) {
		this.role = role;
		this.pmf = pmf;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Pmf getPmf() {
		return pmf;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof UsesRelation) {
			final Uses usesRelation = (Uses) object;
			return getRole().equals(usesRelation.getRole()) && getPmf().equals(usesRelation.getPmf());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getRole(), getPmf());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.RELATION.get(getRole().getId(), getPmf().getId());
		}
		return toString;
	}

}
