package io.github.runtimemodels.chazm.id;

import runtimemodels.chazm.api.id.UniqueId;

import javax.validation.constraints.NotNull;

import static io.github.runtimemodels.chazm.validation.Checks.checkNotNull;

/**
 * The {@linkplain AbstractId} class is an abstract implementation of {@linkplain UniqueId}.
 *
 * @param <T> the type of the {@linkplain UniqueId}
 * @author Christopher Zhong
 * @since 7.0.0
 */
public abstract class AbstractId<T> implements UniqueId<T> {

    private static final long serialVersionUID = 2812867343219462118L;
    private final Class<T> type;
    private transient Integer hashCode = null;
    private transient String toString = null;

    protected AbstractId(@NotNull final Class<T> type) {
        checkNotNull(type, "type"); //$NON-NLS-1$
        this.type = type;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof UniqueId) {
            final UniqueId<?> other = (UniqueId<?>) object;
            return getType().equals(other.getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = getType().hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = getType().getName();
        }
        return toString;
    }

    public Class<T> getType() {
        return this.type;
    }
}
