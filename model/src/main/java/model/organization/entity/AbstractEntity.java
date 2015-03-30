package model.organization.entity;

import javax.validation.constraints.NotNull;

import model.organization.id.Identifiable;
import model.organization.id.UniqueId;

/**
 * The {@linkplain AbstractEntity} provides an abstract class for entities.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @since 7.0.0
 */
public abstract class AbstractEntity<T> implements Identifiable<T> {

	private final UniqueId<T> id;
	private transient Integer hashCode = null;
	private transient String toString = null;

	protected AbstractEntity(@NotNull final UniqueId<T> id) {
		this.id = id;
	}

	@Override
	public UniqueId<T> getId() {
		return id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof AbstractEntity) {
			final AbstractEntity<?> entity = (AbstractEntity<?>) object;
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
			toString = String.format("%s(%s)", getClass().getSimpleName(), getId());
		}
		return toString;
	}

}