package org.models.organization.relation.manager;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.Agent;
import org.models.organization.id.UniqueId;
import org.models.organization.relation.Assignment;

public interface AssignmentManager {
	/**
	 * Adds the given {@linkplain Assignment} to the set of {@linkplain Assignment}.
	 *
	 * @param assignment
	 *            the {@linkplain Assignment} to be added.
	 */
	void addAssignment(Assignment assignment);

	/**
	 * Adds the given set of {@linkplain Assignment} to the set of {@linkplain Assignment}.
	 *
	 * @param assignments
	 *            the set of {@linkplain Assignment} to be added.
	 */
	void addAssignments(Collection<Assignment> assignments);

	/**
	 * Returns the {@linkplain Assignment} by the given {@linkplain UniqueId} that identifies the {@linkplain Assignment} .
	 *
	 * @param id
	 *            the {@linkplain UniqueId} identifying the {@linkplain Assignment} to retrieve.
	 * @return the {@linkplain Assignment} if it exists, <code>null</code> otherwise.
	 */
	Assignment getAssignment(UniqueId<Assignment> id);

	/**
	 * Returns the set of {@linkplain Assignment}.
	 *
	 * @return the set of {@linkplain Assignment}.
	 */
	Set<Assignment> getAssignments();

	/**
	 * Returns a set of {@linkplain Assignment}s that is assigned to an {@linkplain Agent}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent}.
	 * @return the set of {@linkplain Assignment}s that is assigned the {@linkplain Agent}.
	 */
	Set<Assignment> getAssignmentsByAgent(UniqueId<Agent> id);

	/**
	 * Removes the {@linkplain Assignment} by the given {@linkplain UniqueId} that identifies the {@linkplain Assignment} .
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that identifies the {@linkplain Assignment} to be removed.
	 */
	void removeAssignment(UniqueId<Assignment> id);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain Assignment} from the set of {@linkplain Assignment}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain Assignment} to be removed.
	 */
	void removeAssignments(Collection<UniqueId<Assignment>> ids);

	/**
	 * Clears the set of {@linkplain Assignment}.
	 */
	void removeAllAssignments();

}