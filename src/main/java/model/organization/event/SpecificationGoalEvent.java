package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;

/**
 * The {@linkplain SpecificationGoalEvent} class indicates that there is an update about an {@linkplain SpecificationGoal}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class SpecificationGoalEvent extends AbstractEvent {

	private static final long serialVersionUID = 602095857291387756L;
	private final UniqueId<SpecificationGoal> id;

	/**
	 * Constructs a new instance of {@linkplain SpecificationGoalEvent}.
	 *
	 * @param goal
	 *            the {@linkplain SpecificationGoal}.
	 * @param category
	 *            the category of the update.
	 */
	public SpecificationGoalEvent(final SpecificationGoal goal, final UpdateCategory category) {
		super(category);
		checkNotNull(goal, "goal");
		id = goal.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain SpecificationGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<SpecificationGoal> getId() {
		return id;
	}

}
