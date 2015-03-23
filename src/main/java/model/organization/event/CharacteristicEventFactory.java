package model.organization.event;

import model.organization.entity.Characteristic;

/**
 * The {@linkplain CharacteristicEventFactory} interface defines the API for constructing {@linkplain CharacteristicEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface CharacteristicEventFactory {

	/**
	 * Constructs an {@linkplain CharacteristicEvent}.
	 *
	 * @param characteristic
	 *            the {@linkplain Characteristic}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain CharacteristicEvent}.
	 */
	CharacteristicEvent build(Characteristic characteristic, EventCategory category);

}