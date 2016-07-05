package io.github.runtimemodels.chazm.relation;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.message.M;
import lombok.Getter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class UsesRelation implements Uses {

    @Getter
    private final Role role;
    @Getter
    private final Pmf pmf;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    UsesRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Pmf pmf) {
        this.role = role;
        this.pmf = pmf;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof UsesRelation) {
            final Uses usesRelation = (Uses) object;
            return getRole().equals(usesRelation.getRole()) && getPmf().equals(usesRelation.getPmf());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getRole(), getPmf());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.RELATION.get(getRole().getId(), getPmf().getId());
        }
        return toString;
    }

}
