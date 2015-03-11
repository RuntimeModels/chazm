package org.models.organization.manager;

import java.util.Collection;
import java.util.Set;

import org.models.organization.identifier.UniqueId;
import org.models.organization.relation.Task;

/**
 * The {@linkplain TaskManager} interface defines the necessary APIs for managing {@linkplain Task}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface TaskManager {
	/**
	 * Adds a {@linkplain Task} to this {@linkplain TaskManager}.
	 *
	 * @param taskRelation
	 *            the {@linkplain Task} to add.
	 */
	@Deprecated
	void addTask(Task taskRelation);

	/**
	 * Adds a set of {@linkplain Task}s to this {@linkplain TaskManager}.
	 *
	 * @param tasks
	 *            the set of {@linkplain Task}s to add.
	 */
	@Deprecated
	void addTasks(Collection<Task> tasks);

	/**
	 * Adds a set of {@linkplain Task}s to this {@linkplain TaskManager}.
	 *
	 * @param tasks
	 *            the set of {@linkplain Task}s to add.
	 */
	@Deprecated
	void addTasks(Task... tasks);

	/**
	 * Returns a {@linkplain Task} from this {@linkplain TaskManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Task} to retrieve.
	 * @return the {@linkplain Task} if it exists, <code>null</code> otherwise.
	 */
	Task getTask(UniqueId id);

	/**
	 * Returns a set of {@linkplain Task}s from this {@linkplain TaskManager}.
	 *
	 * @return the set of {@linkplain Task}s.
	 */
	Set<Task> getTasks();

	/**
	 * Removes a {@linkplain Task} from this {@linkplain TaskManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Task} to remove.
	 */
	@Deprecated
	void removeTask(UniqueId id);

	/**
	 * Removes all {@linkplain Task}s from this {@linkplain TaskManager}.
	 */
	@Deprecated
	void removeAllTasks();
}