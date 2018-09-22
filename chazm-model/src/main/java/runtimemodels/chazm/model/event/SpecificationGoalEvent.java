package runtimemodels.chazm.model.event;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The {@linkplain SpecificationGoalEvent} class indicates that there is an update about an {@linkplain SpecificationGoal}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class SpecificationGoalEvent extends AbstractEvent {

    private static final long serialVersionUID = 602095857291387756L;
    private final UniqueId<SpecificationGoal> id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    SpecificationGoalEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final SpecificationGoal goal) {
        super(category);
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

    @Override
    public boolean equals(final Object object) {
        if (object instanceof SpecificationGoalEvent) {
            final SpecificationGoalEvent event = (SpecificationGoalEvent) object;
            return super.equals(event) && getId().equals(event.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getCategory(), getId());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.EVENT_WITH_1_ID.get(super.toString(), getId());
        }
        return toString;
    }

}
