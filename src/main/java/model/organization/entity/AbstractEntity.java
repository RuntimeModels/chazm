package model.organization.entity;

import static model.organization.validation.Checks.checkNotNull;

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
public class AbstractEntity<T> implements Identifiable<T> {

	private final UniqueId<T> id;

	protected AbstractEntity(@NotNull final UniqueId<T> id) {
		checkNotNull(id, "id");
		this.id = id;
	}

	@Override
	public UniqueId<T> getId() {
		return id;
	}

}