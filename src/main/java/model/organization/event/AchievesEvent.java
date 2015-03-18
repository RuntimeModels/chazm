package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;
import model.organization.relation.Achieves;

/**
 * The {@linkplain AchievesEvent} class indicates that there is an update about an {@linkplain Achieves} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class AchievesEvent extends AbstractEvent {

	private static final long serialVersionUID = -6277684926586766989L;
	private final UniqueId<Role> roleId;
	private final UniqueId<SpecificationGoal> goalId;

	/**
	 * Constructs a new instance of {@linkplain AchievesEvent}.
	 *
	 * @param achieves
	 *            the {@linkplain Achieves}.
	 * @param category
	 *            the category of the update.
	 */
	public AchievesEvent(final Achieves achieves, final UpdateCategory category) {
		super(category);
		checkNotNull(achieves, "achieves");
		roleId = achieves.getRole().getId();
		goalId = achieves.getGoal().getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Role}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Role> getRoleId() {
		return roleId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain SpecificationGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<SpecificationGoal> getGoalId() {
		return goalId;
	}

}
