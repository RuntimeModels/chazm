package model.organization.event;

import model.organization.entity.Attribute;

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
	 * @param attribute
	 *            the {@linkplain Attribute}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain AttributeEvent}.
	 */
	AttributeEvent build(Attribute attribute, EventCategory category);

}