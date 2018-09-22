package runtimemodels.chazm.model.relation;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Uses;
import runtimemodels.chazm.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class UsesRelation implements Uses {

    private final Role role;
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

    public Role getRole() {
        return this.role;
    }

    public Pmf getPmf() {
        return this.pmf;
    }
}
