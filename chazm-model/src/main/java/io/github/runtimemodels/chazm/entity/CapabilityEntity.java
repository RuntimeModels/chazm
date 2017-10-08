package io.github.runtimemodels.chazm.entity;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.id.UniqueId;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

class CapabilityEntity extends AbstractEntity<Capability> implements Capability {

    @Inject
    CapabilityEntity(@NotNull @Assisted final UniqueId<Capability> id) {
        super(id);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Capability) {
            final Capability capability = (Capability) object;
            return super.equals(capability);
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
