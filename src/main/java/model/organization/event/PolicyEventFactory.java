package model.organization.event;

import model.organization.entity.Policy;

/**
 * The {@linkplain PolicyEventFactory} interface defines the API for constructing {@linkplain PolicyEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PolicyEventFactory {

	/**
	 * Constructs an {@linkplain PolicyEvent}.
	 *
	 * @param policy
	 *            the {@linkplain Policy}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain PolicyEvent}.
	 */
	PolicyEvent build(Policy policy, EventCategory category);

}