package model.organization.relations.managers;

import java.util.Set;

import model.organization.id.UniqueId;
import model.organization.relations.Task;

/**
 * The {@linkplain TaskManager} interface defines the necessary APIs for managing {@linkplain Task}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface TaskManager {
	/**
	 * Returns a {@linkplain Task} from this {@linkplain TaskManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Task} to retrieve.
	 * @return the {@linkplain Task} if it exists, <code>null</code> otherwise.
	 */
	Task getTask(UniqueId<Task> id);

	/**
	 * Returns a set of {@linkplain Task}s from this {@linkplain TaskManager}.
	 *
	 * @return the set of {@linkplain Task}s.
	 */
	Set<Task> getTasks();
}