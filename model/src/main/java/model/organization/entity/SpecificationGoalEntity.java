package model.organization.entity;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class SpecificationGoalEntity extends AbstractEntity<SpecificationGoal> implements SpecificationGoal {

	private transient String toString = null;

	@Inject
	SpecificationGoalEntity(@NotNull @Assisted final UniqueId<SpecificationGoal> id) {
		super(id);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SpecificationGoal) {
			final SpecificationGoal goal = (SpecificationGoal) object;
			return super.equals(goal);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s)", getClass().getSimpleName(), getId());
		}
		return toString;
	}

}
