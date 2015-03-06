/*
 * IntegerIdentifier.java
 *
 * Created on Feb 7, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.identifier;

/**
 * The <code>IntegerIdentifier</code> implements the {@link UniqueIdentifier} interface to represent <code>int</code> as entities in the Organization Model.
 *
 * @author Christopher Zhong
 * @since 4.0
 * @see UniqueIdentifier
 */
public class IntegerIdentifier extends UniqueIdentifier {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The <code>long</code> that is used as the <code>UniqueIdentifier</code>.
	 */
	private final int value;

	/**
	 * Optimization for the <code>toString()</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>IntegerIdentifier</code>.
	 *
	 * @param value
	 *            the <code>int</code> that is used as the <code>UniqueIdentifier</code>.
	 */
	public IntegerIdentifier(final int value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof IntegerIdentifier) {
			final IntegerIdentifier integerIdentifier = (IntegerIdentifier) object;
			return value == integerIdentifier.value;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.valueOf(value);
		}
		return toString;
	}

}
