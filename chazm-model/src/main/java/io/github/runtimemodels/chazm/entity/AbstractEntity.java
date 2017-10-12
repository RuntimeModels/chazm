package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.id.Identifiable;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.M;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The {@linkplain AbstractEntity} provides an abstract class for entities.
 *
 * @param <T> the type of the {@linkplain UniqueId}.
 * @author Christopher Zhong
 * @since 7.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEntity<T> implements Identifiable<T> {

    @Getter
    private final UniqueId<T> id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Identifiable) {
            final Identifiable<?> entity = (Identifiable<?>) object;
            return getId().equals(entity.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = getId().hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.ENTITY_0.get(getClass().getSimpleName(), getId());
        }
        return toString;
    }

}
