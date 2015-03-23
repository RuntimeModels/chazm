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
	 * @param assignment
	 *            the {@linkplain Assignment}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain AssignmentEvent}.
	 */
	AssignmentEvent build(Assignment assignment, EventCategory category);

}