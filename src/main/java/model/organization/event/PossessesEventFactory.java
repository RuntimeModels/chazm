package model.organization.event;

import model.organization.relation.Possesses;

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
	 * @param possesses
	 *            the {@linkplain Possesses}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain PossessesEvent}.
	 */
	PossessesEvent build(Possesses possesses, EventCategory category);

}