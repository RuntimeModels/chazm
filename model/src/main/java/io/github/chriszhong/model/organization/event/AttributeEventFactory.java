package io.github.chriszhong.model.organization.event;

import io.github.runtimemodels.chazm.entity.Attribute;

/**
 * The {@linkplain AttributeEventFactory} interface defines the API for constructing {@linkplain AttributeEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AttributeEventFactory {

	/**
	 * Constructs an {@linkplain AttributeEvent}.
	 *
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param attribute
	 *            the {@linkplain Attribute}.
	 *
	 * @return a {@linkplain AttributeEvent}.
	 */
	AttributeEvent build(EventCategory category, Attribute attribute);

}