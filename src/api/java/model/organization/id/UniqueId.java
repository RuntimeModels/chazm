package model.organization.id;

import java.io.Serializable;

import model.organization.Organization;

/**
 * The {@linkplain UniqueId} is class that allows customizable ways for identifying elements of an {@linkplain Organization}.
 * <p>
 * Implementations of {@linkplain UniqueId} are not required to ensure that instances of {@linkplain UniqueId} are unique because that is the responsibility of
 * an {@linkplain Organization}.
 * <p>
 * In the typical case, uniqueness is ensured by the parameter that is passed to the constructor of a subclass of {@linkplain UniqueId}. For example, if
 * <code>x = y</code>, then <code>UniqueId(x).equals(UniqueId(y))</code> should return <code>true</code>. But that does not mean that
 * <code>UniqueId(x) = UniqueId(y)</code>.
 * <p>
 * Should a developer wish to ensure that if <code>x = y</code> then <code>UniqueId(x) = UniqueId(y)</code>, the developer should ensure that such an
 * implementation can be used correctly across multiple JVMs because {@linkplain UniqueId} is {@linkplain Serializable}, so {@linkplain UniqueId} should
 * de-serialize properly.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @since 4.0
 */
@FunctionalInterface
public interface UniqueId<T> extends Serializable {
	/**
	 * Returns the type of this {@linkplain UniqueId}.
	 *
	 * @return the type.
	 */
	Class<T> getType();

	@Override
	boolean equals(Object object);

	@Override
	int hashCode();

	@Override
	String toString();
}