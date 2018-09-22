package runtimemodels.chazm.model.relation;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.api.relation.Moderates;
import runtimemodels.chazm.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class ModeratesRelation implements Moderates {

    private final Pmf pmf;
    private final Attribute attribute;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    ModeratesRelation(@NotNull @Assisted final Pmf pmf, @NotNull @Assisted final Attribute attribute) {
        this.pmf = pmf;
        this.attribute = attribute;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Moderates) {
            final Moderates moderates = (Moderates) object;
            return getPmf().equals(moderates.getPmf()) && getAttribute().equals(moderates.getAttribute());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getPmf(), getAttribute());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.RELATION.get(getPmf().getId(), getAttribute().getId());
        }
        return toString;
    }

    public Pmf getPmf() {
        return this.pmf;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }
}
