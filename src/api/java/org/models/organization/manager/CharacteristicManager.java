package org.models.organization.manager;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.Characteristic;
import org.models.organization.identifier.UniqueId;

/**
 * The @linkplain CharacteristicManager} interface defines the necessary APIs for managing {@linkplain Characteristic}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface CharacteristicManager {
	/**
	 * Adds a {@linkplain Characteristic} to this {@linkplain CharacteristicManager}.
	 *
	 * @param characteristic
	 *            the {@linkplain Characteristic} to add.
	 */
	void addCharacteristic(Characteristic characteristic);

	/**
	 * Adds a set of {@linkplain Characteristic}s to this {@linkplain CharacteristicManager}.
	 *
	 * @param characteristics
	 *            the set of {@linkplain Characteristic} to add.
	 */
	void addCharacteristics(Collection<Characteristic> characteristics);

	/**
	 * Adds a set of {@linkplain Characteristic}s to this {@linkplain CharacteristicManager}.
	 *
	 * @param characteristics
	 *            the set of {@linkplain Characteristic} to add.
	 */
	void addCharacteristics(Characteristic... characteristics);

	/**
	 * Returns a {@linkplain Characteristic} from this {@linkplain CharacteristicManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Characteristic} to retrieve.
	 * @return the {@linkplain Characteristic} if it exists, <code>null</code> otherwise.
	 */
	Characteristic getCharacteristic(UniqueId id);

	/**
	 * Returns a set of {@linkplain Characteristic}s from this {@linkplain CharacteristicManager}.
	 *
	 * @return the set of {@linkplain Characteristic}s.
	 */
	Set<Characteristic> getCharacteristics();

	/**
	 * Removes a {@linkplain Characteristic} from this {@linkplain CharacteristicManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Characteristic} to remove.
	 */
	void removeCharacteristic(UniqueId id);

	/**
	 * Removes all {@linkplain Characteristic}s from this {@linkplain CharacteristicManager}.
	 */
	void removeAllCharacteristic();
}