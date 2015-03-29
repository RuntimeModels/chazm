package model.organization.event;

import model.organization.relation.Assignment;

/**
 * The {@linkplain AssignmentEventFactory} interface defines the API for constructing {@linkplain AssignmentEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AssignmentEventFactory {

	/**
	 * Constructs an {@linkplain AssignmentEvent}.
	 *
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param assignment
	 *            the {@linkplain Assignment}.
	 *
	 * @return a {@linkplain AssignmentEvent}.
	 */
	AssignmentEvent build(EventCategory category, Assignment assignment);

}