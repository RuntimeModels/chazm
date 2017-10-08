package io.github.runtimemodels.chazm.relation;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.message.M;
import lombok.Getter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class AchievesRelation implements Achieves {

    @Getter
    private final Role role;
    @Getter
    private final SpecificationGoal goal;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    AchievesRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final SpecificationGoal goal) {
        this.role = role;
        this.goal = goal;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Achieves) {
            final Achieves achieves = (Achieves) object;
            return getRole().equals(achieves.getRole()) && getGoal().equals(achieves.getGoal());

        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getRole(), getGoal());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.RELATION.get(getRole().getId(), getGoal().getId());
        }
        return toString;
    }

}
