package model.organization.entity;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;
import model.organization.relation.Assignment;

import com.google.inject.assistedinject.Assisted;

class PmfEntity extends AbstractEntity<Pmf> implements Pmf {

	@Inject
	PmfEntity(@NotNull @Assisted final UniqueId<Pmf> id) {
		super(id);
	}

	@Override
	public final Double pmf(final Agent agent, final Role role, final InstanceGoal goal, final Collection<Assignment> assignments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Pmf) {
			final Pmf pmf = (Pmf) object;
			return getId().equals(pmf.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}

}
