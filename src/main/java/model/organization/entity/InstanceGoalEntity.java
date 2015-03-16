package model.organization.entity;

import static model.organization.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.AbstractId;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class InstanceGoalEntity extends AbstractEntity<InstanceGoal> implements InstanceGoal {

	/**
	 * The {@linkplain Id} class extends the {@link AbstractId} by using two {@link UniqueId}s; the {@linkplain UniqueId} of a {@linkplain SpecificationGoal}
	 * and the {@linkplain UniqueId} of instance portion of the {@linkplain InstanceGoal}.
	 *
	 * @author Christopher Zhong
	 * @since 4.0
	 * @see UniqueId
	 */
	public static class Id extends AbstractId<InstanceGoal> {

		private static final long serialVersionUID = 1735177133145542202L;
		private final UniqueId<SpecificationGoal> specificationId;
		private final UniqueId<InstanceGoal> instanceId;
		private transient Integer hashCode = null;
		private transient String toString = null;

		@Inject
		Id(@NotNull @Assisted final UniqueId<SpecificationGoal> specificationId, @NotNull @Assisted final UniqueId<InstanceGoal> instanceId) {
			super(InstanceGoal.class);
			checkNotNull(specificationId, "specificationId");
			checkNotNull(instanceId, "instanceId");
			this.specificationId = specificationId;
			this.instanceId = instanceId;
		}

		private UniqueId<SpecificationGoal> getSpecificationId() {
			return specificationId;
		}

		private UniqueId<InstanceGoal> getInstanceId() {
			return instanceId;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof InstanceGoalEntity.Id) {
				final InstanceGoalEntity.Id id = (InstanceGoalEntity.Id) object;
				return getSpecificationId().equals(id.getSpecificationId()) && getInstanceId().equals(id.getInstanceId());
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hashCode == null) {
				hashCode = getSpecificationId().hashCode() << 16 | getInstanceId().hashCode();
			}
			return hashCode;
		}

		@Override
		public String toString() {
			if (toString == null) {
				toString = String.format("%s[%s]", getSpecificationId(), getInstanceId());
			}
			return toString;
		}

	}

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
		final Parameter parameter = getParameter();
		return String.format("%s (%s)", getId().toString(), parameter == null ? "" : parameter);
	}

}