/*
 * LongIdentifier.java
 *
 * Created on Feb 7, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.identifier;

/**
 * The <code>LongIdentifier</code> implements the {@link UniqueId} interface to represent <code>long</code> as entities in the Organization Model.
 *
 * @author Christopher Zhong
 * @since 4.0
 * @see UniqueId
 */
public class LongIdentifier extends UniqueId {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The <code>long</code> that is used as the <code>UniqueIdentifier</code>.
	 */
	private final long value;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private transient Integer hashCode = null;

	/**
	 * Optimization for the <code>toString()</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>LongIdentifier</code>.
	 *
	 * @param value
	 *            the <code>long</code> that is used as the <code>UniqueIdentifier</code>.
	 */
	public LongIdentifier(final long value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof LongIdentifier) {
			final LongIdentifier longIdentifier = (LongIdentifier) object;
			return value == longIdentifier.value;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = (int) value;
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.valueOf(value);
		}
		return toString;
	}

}
