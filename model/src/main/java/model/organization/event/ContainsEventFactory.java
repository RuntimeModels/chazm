package model.organization.event;

import model.organization.relation.Contains;

/**
 * The {@linkplain ContainsEventFactory} interface defines the API for constructing {@linkplain ContainsEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface ContainsEventFactory {

	/**
	 * Constructs an {@linkplain ContainsEvent}.
	 *
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param contains
	 *            the {@linkplain Contains}.
	 *
	 * @return a {@linkplain ContainsEvent}.
	 */
	ContainsEvent build(EventCategory category, Contains contains);

}