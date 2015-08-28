package io.github.runtimemodels.chazm.id;

import io.github.runtimemodels.chazm.Organization;

/**
 * The {@linkplain Identifiable} interface is used to mark elements of an {@linkplain Organization} that can be uniquely identified.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @since 7.0.0
 */
@FunctionalInterface
public interface Identifiable<T> {
	/**
	 * Returns the {@linkplain UniqueId}.
	 *
	 * @return the {@linkplain UniqueId}.
	 */
	UniqueId<T> getId();
}
