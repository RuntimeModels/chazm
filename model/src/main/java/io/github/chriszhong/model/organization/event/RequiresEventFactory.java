package io.github.chriszhong.model.organization.event;

import io.github.runtimemodels.chazm.relation.Requires;

/**
 * The {@linkplain RequiresEventFactory} interface defines the API for constructing {@linkplain RequiresEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface RequiresEventFactory {

	/**
	 * Constructs an {@linkplain RequiresEvent}.
	 *
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param requires
	 *            the {@linkplain Requires}.
	 *
	 * @return a {@linkplain RequiresEvent}.
	 */
	RequiresEvent build(EventCategory category, Requires requires);

}