package model.organization.event;

import model.organization.relation.Needs;

/**
 * The {@linkplain NeedsEventFactory} interface defines the API for constructing {@linkplain NeedsEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface NeedsEventFactory {

	/**
	 * Constructs an {@linkplain NeedsEvent}.
	 *
	 * @param needs
	 *            the {@linkplain Needs}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain NeedsEvent}.
	 */
	NeedsEvent build(Needs needs, EventCategory category);

}