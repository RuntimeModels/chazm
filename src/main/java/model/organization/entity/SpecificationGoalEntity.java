package model.organization.entity;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class SpecificationGoalEntity extends AbstractEntity<SpecificationGoal> implements SpecificationGoal {

	@Inject
	SpecificationGoalEntity(@NotNull @Assisted final UniqueId<SpecificationGoal> id) {
		super(id);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SpecificationGoal) {
			final SpecificationGoal goal = (SpecificationGoal) object;
			return getId().equals(goal.getId());
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
