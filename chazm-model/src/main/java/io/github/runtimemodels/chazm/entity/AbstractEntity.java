package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.message.M;
import runtimemodels.chazm.api.id.Identifiable;
import runtimemodels.chazm.api.id.UniqueId;

/**
 * The {@linkplain AbstractEntity} provides an abstract class for entities.
 *
 * @param <T> the type of the {@linkplain UniqueId}.
 * @author Christopher Zhong
 * @since 7.0.0
 */
public abstract class AbstractEntity<T> implements Identifiable<T> {

    private final UniqueId<T> id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @java.beans.ConstructorProperties({"id"})
    public AbstractEntity(UniqueId<T> id) {
        this.id = id;
    }

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

    public UniqueId<T> getId() {
        return this.id;
    }
}
