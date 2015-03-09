/*
 * ClassIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.identifier;

/**
 * The <code>ClassIdentifier</code> implements the {@link UniqueId} interface to represent {@link Class} as entities in the Organization Model.
 *
 * @author Christopher Zhong
 * @since 4.0
 * @see UniqueId
 */
public class ClassIdentifier extends UniqueId {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The <code>Class</code> that is used as the <code>UniqueIdentifier</code>.
	 */
	private final Class<?> classType;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private transient Integer hashCode = null;

	/**
	 * Optimization for the <code>toString()</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>ClassIdentifier</code>.
	 *
	 * @param classType
	 *            the <code>Class</code> that is used as the <code>UniqueIdentifier</code>.
	 */
	public ClassIdentifier(final Class<?> classType) {
		this.classType = classType;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof ClassIdentifier) {
			final ClassIdentifier classIdentifier = (ClassIdentifier) object;
			return classType.equals(classIdentifier.classType);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = classType.hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = classType.getName();
		}
		return toString;
	}

}
