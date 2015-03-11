/*
 * IntegerIdentifier.java
 *
 * Created on Feb 7, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.id;

import org.models.organization.id.UniqueId;

/**
 * The {@linkplain IntId} extends the {@link UniqueId} by using a <code>int</code> as the id.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @see UniqueId
 * @since 4.0
 */
public class IntId<T> extends UniqueId<T> {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -5052735277036535425L;

	/**
	 * The <code>long</code> of this {@linkplain IntId}.
	 */
	private final int value;

	/**
	 * Optimization for the <code>toString()</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of {@linkplain IntId}.
	 *
	 * @param value
	 *            the <code>int</code> of this {@linkplain IntId}.
	 */
	public IntId(final int value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof IntId) {
			final IntId<?> otherId = (IntId<?>) object;
			return value == otherId.value;
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
