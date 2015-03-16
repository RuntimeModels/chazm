/*
 * CharacteristicImpl.java
 *
 * Created on May 25, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.entities;

import model.organization.NullCheck;
import model.organization.id.UniqueId;

/**
 * The {@link CharacteristicEntity} class is an implementation of the {@link Characteristic}.
 *
 * @author Christopher Zhong
 * @see Characteristic
 * @since 6.0
 */
public class CharacteristicEntity implements Characteristic, NullCheck {
	/**
	 * The {@linkplain UniqueId} that represents this {@link Characteristic}.
	 */
	private final UniqueId<Characteristic> id;

	/**
	 * Constructs a new instance of {@link Characteristic}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@link Characteristic}.
	 */
	public CharacteristicEntity(final UniqueId<Characteristic> id) {
		checkNotNull(id, "id");
		this.id = id;
	}

	@Override
	public final UniqueId<Characteristic> getId() {
		return id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Characteristic) {
			final Characteristic characteristic = (Characteristic) object;
			return getId().equals(characteristic.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}
}
