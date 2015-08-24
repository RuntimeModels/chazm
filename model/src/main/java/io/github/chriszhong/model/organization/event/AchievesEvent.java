package io.github.chriszhong.model.organization.event;

import io.github.chriszhong.message.M;
import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.entity.SpecificationGoal;
import io.github.chriszhong.model.organization.id.UniqueId;
import io.github.chriszhong.model.organization.relation.Achieves;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

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
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	AchievesEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Achieves achieves) {
		super(category);
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

	@Override
	public boolean equals(final Object object) {
		if (object instanceof AchievesEvent) {
			final AchievesEvent event = (AchievesEvent) object;
			return super.equals(event) && getRoleId().equals(event.getRoleId()) && getGoalId().equals(event.getGoalId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getCategory(), getRoleId(), getGoalId());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_2_IDS.get(super.toString(), getRoleId(), getGoalId());
		}
		return toString;
	}

}
