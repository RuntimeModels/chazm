package runtimemodels.chazm.model.entity;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.api.id.UniqueId;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

class PmfEntity extends AbstractEntity<Pmf> implements Pmf {

    @Inject
    PmfEntity(@NotNull @Assisted final UniqueId<Pmf> id) {
        super(id);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Pmf) {
            final Pmf pmf = (Pmf) object;
            return super.equals(pmf);
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
