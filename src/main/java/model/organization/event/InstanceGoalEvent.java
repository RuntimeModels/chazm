package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.validation.constraints.NotNull;

import model.organization.entity.InstanceGoal;
import model.organization.entity.InstanceGoal.Parameter;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;

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

	/**
	 * Constructs a new instance of {@linkplain InstanceGoalEvent}.
	 *
	 * @param goal
	 *            the {@linkplain InstanceGoal}.
	 * @param category
	 *            the category of the update.
	 */
	public InstanceGoalEvent(@NotNull final InstanceGoal goal, @NotNull final UpdateCategory category) {
		super(category);
		checkNotNull(goal, "goal");
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

}
