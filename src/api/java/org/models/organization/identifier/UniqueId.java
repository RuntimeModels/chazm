/*
 * UniqueIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.identifier;

import java.io.Serializable;

import org.models.organization.Organization;

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
 * @since 4.0
 */
public abstract class UniqueId implements Serializable {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();

	/**
	 * Utilizes the <code>equals</code> method from <code>Object</code> to determine equality.
	 *
	 * @param object
	 *            the <code>Object</code> with which to compare.
	 * @return <code>true</code> if this object is the same as the given <code>Object</code>, <code>false</code> otherwise.
	 * @see Object#equals(Object)
	 */
	public final boolean getObjectEquals(final Object object) {
		return super.equals(object);
	}

	/**
	 * Utilizes the <code>hashCode</code> method from <code>Object</code> to compute the hash code.
	 *
	 * @return the hash code value.
	 * @see Object#hashCode()
	 */
	public final int getObjectHashCode() {
		return super.hashCode();
	}

	/**
	 * Utilizes the <code>toString</code> method from <code>Object</code> to return a <code>String</code> representation.
	 *
	 * @return a <code>String</code> representation.
	 * @see Object#toString()
	 */
	public final String getObjectToString() {
		return super.toString();
	}
}