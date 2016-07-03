package io.github.runtimemodels.chazm.id;

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

    protected AbstractId(@NotNull final Class<T> type) {
        checkNotNull(type, "type"); //$NON-NLS-1$
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
