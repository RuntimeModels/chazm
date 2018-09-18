package io.github.runtimemodels.chazm.relation;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.message.M;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Needs;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class NeedsRelation implements Needs {

    private final Role role;
    private final Attribute attribute;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    NeedsRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Attribute attribute) {
        this.role = role;
        this.attribute = attribute;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Needs) {
            final Needs needs = (Needs) object;
            return getRole().equals(needs.getRole()) && getAttribute().equals(needs.getAttribute());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getRole(), getAttribute());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.RELATION.get(getRole().getId(), getAttribute().getId());
        }
        return toString;
    }

    public Role getRole() {
        return this.role;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }
}
