package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.M;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

class InstanceGoalEntity extends AbstractEntity<InstanceGoal> implements InstanceGoal {

	private final SpecificationGoal goal;
	private final InstanceGoal.Parameter parameter;
	private transient Integer hashCode = null;

	@Inject
	InstanceGoalEntity(@NotNull @Assisted final UniqueId<InstanceGoal> id, @NotNull @Assisted final SpecificationGoal goal,
			@NotNull @Assisted final InstanceGoal.Parameter parameter) {
		super(id);
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
			return super.equals(goal) && getGoal().equals(goal.getGoal());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(super.hashCode(), getGoal());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return M.ENTITY_2.get(super.toString(), getGoal(), getParameter());
	}

}