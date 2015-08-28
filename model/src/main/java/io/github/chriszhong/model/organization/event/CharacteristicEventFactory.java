package io.github.chriszhong.model.organization.event;

import io.github.runtimemodels.chazm.entity.Characteristic;

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
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param characteristic
	 *            the {@linkplain Characteristic}.
	 *
	 * @return a {@linkplain CharacteristicEvent}.
	 */
	CharacteristicEvent build(EventCategory category, Characteristic characteristic);

}