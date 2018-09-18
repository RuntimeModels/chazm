package io.github.runtimemodels.chazm.entity;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.id.UniqueId;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

class CharacteristicEntity extends AbstractEntity<Characteristic> implements Characteristic {

    @Inject
    CharacteristicEntity(@NotNull @Assisted final UniqueId<Characteristic> id) {
        super(id);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Characteristic) {
            final Characteristic characteristic = (Characteristic) object;
            return super.equals(characteristic);
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
