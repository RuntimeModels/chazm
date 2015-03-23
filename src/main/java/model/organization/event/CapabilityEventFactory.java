package model.organization.event;

import model.organization.entity.Capability;

/**
 * The {@linkplain CapabilityEventFactory} interface defines the API for constructing {@linkplain CapabilityEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface CapabilityEventFactory {

	/**
	 * Constructs an {@linkplain CapabilityEvent}.
	 *
	 * @param capability
	 *            the {@linkplain Capability}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain CapabilityEvent}.
	 */
	CapabilityEvent build(Capability capability, EventCategory category);

}