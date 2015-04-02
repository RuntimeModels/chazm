package model.organization.relation;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import message.M;
import model.organization.entity.Attribute;
import model.organization.entity.Pmf;

import com.google.inject.assistedinject.Assisted;

class ModeratesRelation implements Moderates {

	private final Pmf pmf;
	private final Attribute attribute;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	ModeratesRelation(@NotNull @Assisted final Pmf pmf, @NotNull @Assisted final Attribute attribute) {
		this.pmf = pmf;
		this.attribute = attribute;
	}

	@Override
	public Pmf getPmf() {
		return pmf;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Moderates) {
			final Moderates moderates = (Moderates) object;
			return getPmf().equals(moderates.getPmf()) && getAttribute().equals(moderates.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getPmf(), getAttribute());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.RELATION.get(getPmf().getId(), getAttribute().getId());
		}
		return toString;
	}

}
