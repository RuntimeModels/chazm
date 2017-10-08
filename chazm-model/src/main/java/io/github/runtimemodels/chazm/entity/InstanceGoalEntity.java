package io.github.runtimemodels.chazm.entity;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.M;
import lombok.Getter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class InstanceGoalEntity extends AbstractEntity<InstanceGoal> implements InstanceGoal {

    @Getter
    private final SpecificationGoal goal;
    @Getter
    private final Parameter parameter;
    private transient Integer hashCode = null;

    @Inject
    InstanceGoalEntity(@NotNull @Assisted final UniqueId<InstanceGoal> id, @NotNull @Assisted final SpecificationGoal goal,
                       @NotNull @Assisted final Parameter parameter) {
        super(id);
        this.goal = goal;
        this.parameter = parameter;
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
