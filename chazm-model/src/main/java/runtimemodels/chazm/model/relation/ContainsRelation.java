package runtimemodels.chazm.model.relation;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Contains;
import runtimemodels.chazm.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class ContainsRelation implements Contains {

    private final Role role;
    private final Characteristic characteristic;
    private double value;
    private transient Integer hashCode = null;

    @Inject
    ContainsRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Characteristic characteristic, @NotNull @Assisted final double value) {
        this.role = role;
        this.characteristic = characteristic;
        this.value = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Contains) {
            final Contains contains = (Contains) object;
            return getRole().equals(contains.getRole()) && getCharacteristic().equals(contains.getCharacteristic());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getRole(), getCharacteristic());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return M.RELATION_WITH_VALUE.get(getRole().getId(), getCharacteristic().getId(), getValue());
    }

    public Role getRole() {
        return this.role;
    }

    public Characteristic getCharacteristic() {
        return this.characteristic;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
