package model.organization.event;

import model.organization.relation.Uses;

/**
 * The {@linkplain UsesEventFactory} interface defines the API for constructing {@linkplain UsesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface UsesEventFactory {

	/**
	 * Constructs an {@linkplain UsesEvent}.
	 *
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param uses
	 *            the {@linkplain Uses}.
	 *
	 * @return a {@linkplain UsesEvent}.
	 */
	UsesEvent build(EventCategory category, Uses uses);

}