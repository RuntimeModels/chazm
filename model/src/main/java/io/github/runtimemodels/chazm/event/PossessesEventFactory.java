package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.relation.Possesses;

/**
 * The {@linkplain PossessesEventFactory} interface defines the API for constructing {@linkplain PossessesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PossessesEventFactory {

	/**
	 * Constructs an {@linkplain PossessesEvent}.
	 *
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param possesses
	 *            the {@linkplain Possesses}.
	 *
	 * @return a {@linkplain PossessesEvent}.
	 */
	PossessesEvent build(EventCategory category, Possesses possesses);

}