package io.github.runtimemodels.chazm.entity;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.id.UniqueId;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

class SpecificationGoalEntity extends AbstractEntity<SpecificationGoal> implements SpecificationGoal {

    @Inject
    SpecificationGoalEntity(@NotNull @Assisted final UniqueId<SpecificationGoal> id) {
        super(id);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof SpecificationGoal) {
            final SpecificationGoal goal = (SpecificationGoal) object;
            return super.equals(goal);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
