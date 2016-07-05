package io.github.runtimemodels.chazm.id;

import com.google.inject.assistedinject.Assisted;
import lombok.Getter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static io.github.runtimemodels.chazm.validation.Checks.checkNotNull;

class StringId<T> extends AbstractId<T> {

    private static final long serialVersionUID = 522905742372399827L;
    @Getter
    private final String id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    StringId(@NotNull @Assisted final Class<T> type, @NotNull @Assisted final String id) {
        super(type);
        checkNotNull(id, "id"); //$NON-NLS-1$
        this.id = id;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof StringId) {
            final StringId<?> other = (StringId<?>) object;
            return super.equals(other) && getId().equals(other.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(super.hashCode(), getId());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = super.toString() + ":" + getId();
        }
        return toString;
    }

}
