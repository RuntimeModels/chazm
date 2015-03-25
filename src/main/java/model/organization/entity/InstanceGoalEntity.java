package model.organization.entity;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class InstanceGoalEntity extends AbstractEntity<InstanceGoal> implements InstanceGoal {

	private final SpecificationGoal goal;
	private final InstanceGoal.Parameter parameter;

	@Inject
	InstanceGoalEntity(@NotNull @Assisted final UniqueId<InstanceGoal> id, @NotNull @Assisted final SpecificationGoal goal,
			@NotNull @Assisted final InstanceGoal.Parameter parameter) {
		super(id);
		checkNotNull(goal, "goal");
		checkNotNull(parameter, "parameter");
		this.goal = goal;
		this.parameter = parameter;
	}

	@Override
	public SpecificationGoal getGoal() {
		return goal;
	}

	@Override
	public InstanceGoal.Parameter getParameter() {
		return parameter;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof InstanceGoal) {
			final InstanceGoal goal = (InstanceGoal) object;
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
		return String.format("%s(%s, %s, %s)", getClass().getSimpleName(), getId(), getGoal(), getParameter());
	}

}