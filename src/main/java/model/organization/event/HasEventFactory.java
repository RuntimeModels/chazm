package model.organization.event;

import model.organization.relation.Has;

/**
 * The {@linkplain HasEventFactory} interface defines the API for constructing {@linkplain HasEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface HasEventFactory {

	/**
	 * Constructs an {@linkplain HasEvent}.
	 *
	 * @param has
	 *            the {@linkplain Has}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain HasEvent}.
	 */
	HasEvent build(Has has, EventCategory category);

}