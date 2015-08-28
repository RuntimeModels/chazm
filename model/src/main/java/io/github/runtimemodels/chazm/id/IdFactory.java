package io.github.runtimemodels.chazm.id;

/**
 * The {@linkplain IdFactory} interface defines the APIs for constructing {@linkplain UniqueId}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface IdFactory {

	/**
	 * Constructs an {@linkplain UniqueId}.
	 *
	 * @param clazz
	 *            the type of the {@linkplain UniqueId}.
	 * @param id
	 *            the {@linkplain Class} id for the {@linkplain UniqueId}.
	 * @return an {@linkplain UniqueId}.
	 */
	<T, U> UniqueId<T> build(Class<T> clazz, Class<U> id);

	/**
	 * Constructs an {@linkplain UniqueId}.
	 *
	 * @param clazz
	 *            the type of the {@linkplain UniqueId}.
	 * @param id
	 *            the <code>long</code> id for the {@linkplain UniqueId}.
	 * @return an {@linkplain UniqueId}.
	 */
	<T> UniqueId<T> build(Class<T> clazz, Long id);

	/**
	 * Constructs an {@linkplain UniqueId}.
	 *
	 * @param clazz
	 *            the type of the {@linkplain UniqueId}.
	 * @param id
	 *            the {@linkplain String} id for the {@linkplain UniqueId}.
	 * @return an {@linkplain UniqueId}.
	 */
	<T> UniqueId<T> build(Class<T> clazz, String id);

}
