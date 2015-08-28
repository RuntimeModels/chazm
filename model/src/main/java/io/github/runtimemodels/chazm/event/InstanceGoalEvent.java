package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.InstanceGoal.Parameter;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.M;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain InstanceGoalEvent} class indicates that there is an update about an {@linkplain InstanceGoal} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class InstanceGoalEvent extends AbstractEvent {

	private static final long serialVersionUID = -7178745959528744216L;
	private final UniqueId<InstanceGoal> id;
	private final UniqueId<SpecificationGoal> specificationGoalId;
	private final Parameter parameter;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	InstanceGoalEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final InstanceGoal goal) {
		super(category);
		id = goal.getId();
		specificationGoalId = goal.getGoal().getId();
		parameter = goal.getParameter();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain InstanceGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<InstanceGoal> getId() {
		return id;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain SpecificationGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<SpecificationGoal> getSpecificationGoalId() {
		return specificationGoalId;
	}

	/**
	 * Returns a {@linkplain Parameter}.
	 *
	 * @return a {@linkplain Parameter}.
	 */
	public Parameter getParameter() {
		return parameter;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof InstanceGoalEvent) {
			final InstanceGoalEvent event = (InstanceGoalEvent) object;
			return super.equals(event) && getId().equals(event.getId()) && getSpecificationGoalId().equals(event.getSpecificationGoalId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getCategory(), getId(), getSpecificationGoalId());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_2_IDS_AND_VALUE.get(super.toString(), getId(), getSpecificationGoalId(), getParameter());
		}
		return toString;
	}

}
