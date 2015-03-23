package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain SpecificationGoalEvent} class indicates that there is an update about an {@linkplain SpecificationGoal}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class SpecificationGoalEvent extends AbstractEvent {

	private static final long serialVersionUID = 602095857291387756L;
	private final UniqueId<SpecificationGoal> id;

	@Inject
	SpecificationGoalEvent(@NotNull @Assisted final SpecificationGoal goal, @NotNull @Assisted final EventCategory category) {
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
