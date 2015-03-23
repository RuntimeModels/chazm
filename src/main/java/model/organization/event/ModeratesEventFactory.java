package model.organization.event;

import model.organization.relation.Moderates;

/**
 * The {@linkplain ModeratesEventFactory} interface defines the API for constructing {@linkplain ModeratesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface ModeratesEventFactory {

	/**
	 * Constructs an {@linkplain ModeratesEvent}.
	 *
	 * @param moderates
	 *            the {@linkplain Moderates}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain ModeratesEvent}.
	 */
	ModeratesEvent build(Moderates moderates, EventCategory category);

}