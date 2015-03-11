package org.models.organization.manager;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.PerformanceFunction;
import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain PerformanceFunctionManager} interface defines the necessary APIs for managing {@linkplain PerformanceFunction}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface PerformanceFunctionManager {
	/**
	 * Adds a {@linkplain PerformanceFunction} to this {@linkplain PerformanceFunctionManager}.
	 *
	 * @param performanceFunction
	 *            the {@linkplain PerformanceFunction} to add.
	 */
	void addPerformanceFunction(PerformanceFunction performanceFunction);

	/**
	 * Adds a set of {@linkplain PerformanceFunction}s to this {@linkplain PerformanceFunctionManager}.
	 *
	 * @param performanceFunctions
	 *            the set of {@linkplain PerformanceFunction}s to add.
	 */
	void addPerformanceFunctions(Collection<PerformanceFunction> performanceFunctions);

	/**
	 * Adds a set of {@linkplain PerformanceFunction}s to this {@linkplain PerformanceFunctionManager}.
	 *
	 * @param performanceFunctions
	 *            the set of {@linkplain PerformanceFunction}s to add.
	 */
	void addPerformanceFunctions(PerformanceFunction... performanceFunctions);

	/**
	 * Returns a {@linkplain PerformanceFunction} from this {@linkplain PerformanceFunctionManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain PerformanceFunction} to retrieve.
	 * @return the {@linkplain PerformanceFunction} if it exists, <code>null</code> otherwise.
	 */
	PerformanceFunction getPerformanceFunction(UniqueId id);

	/**
	 * Returns a set of {@linkplain PerformanceFunction}s from this {@linkplain PerformanceFunctionManager}.
	 *
	 * @return the set of {@linkplain PerformanceFunction}s.
	 */
	Set<PerformanceFunction> getPerformanceFunctions();

	/**
	 * Removes a {@linkplain PerformanceFunction} from this {@linkplain PerformanceFunctionManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain PerformanceFunction} to remove.
	 */
	void removePerformanceFunction(UniqueId id);

	/**
	 * Removes all {@linkplain PerformanceFunction}s from this {@linkplain PerformanceFunctionManager}.
	 */
	void removeAllPerformanceFunctions();
}