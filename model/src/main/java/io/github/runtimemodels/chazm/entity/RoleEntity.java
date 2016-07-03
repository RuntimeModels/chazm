package io.github.runtimemodels.chazm.entity;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.id.UniqueId;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

class RoleEntity extends AbstractEntity<Role> implements Role {

    @Inject
    RoleEntity(@NotNull @Assisted final UniqueId<Role> id) {
        super(id);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Role) {
            final Role role = (Role) object;
            return super.equals(role);
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
