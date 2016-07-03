package io.github.runtimemodels.chazm.id;

import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import static io.github.runtimemodels.chazm.validation.Checks.checkNotNull;

class LongId<T> extends AbstractId<T> {

    private static final long serialVersionUID = 8542765061773217208L;
    private final Long id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    LongId(@NotNull @Assisted final Class<T> type, @NotNull @Assisted final Long id) {
        super(type);
        checkNotNull(id, "id"); //$NON-NLS-1$
        this.id = id;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof LongId) {
            final LongId<?> otherId = (LongId<?>) object;
            return id.equals(otherId.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = id.hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = String.valueOf(id);
        }
        return toString;
    }

}
