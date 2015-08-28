package io.github.chriszhong.model.organization.event;

import io.github.runtimemodels.chazm.relation.Has;

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
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param has
	 *            the {@linkplain Has}.
	 *
	 * @return a {@linkplain HasEvent}.
	 */
	HasEvent build(EventCategory category, Has has);

}