/*
 * UniqueIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.identifier;

import java.io.Serializable;

/**
 * The <code>UniqueIdentifier</code> abstract class allows custom mechanisms for identifying the entities of the Organization Model.
 * <p>
 * Implementations of <code>UniqueIdentifier</code> does not need to provide a means of ensuring uniqueness (i.e. the uniqueness property is provided through
 * parameter(s) of the constructor). Thus, there are two possible types of implementations.
 * <p>
 * In the case where uniqueness is provided from the parameter(s) of the constructor, then <code>new UniqueIdentifier(x).equals(new UniqueIdentifier(x))</code>
 * will return <code>true</code> because the parameter <code>x</code> is the same.
 * <p>
 * In the case where uniqueness is ensured by the implementation, then <code>UniqueIdentifier.equals(UniqueIdentifier)</code> returns <code>true</code> if and
 * only if <code>UniqueIdentifier == UniqueIdentifier</code>. It is the responsibility of the programmer of the implementation to provide a means of retrieving
 * the correct instance of the <code>UniqueIdentifier</code> so that uses of the implementation across multiple JVMs exhibit correct behavior.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public abstract class UniqueIdentifier implements Serializable {

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