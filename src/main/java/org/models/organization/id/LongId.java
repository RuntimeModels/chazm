/*
 * LongIdentifier.java
 *
 * Created on Feb 7, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.id;

import org.models.organization.id.UniqueId;

/**
 * The {@linkplain LongId} class extends {@link UniqueId} by using a <code>long</code> as the id.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @see UniqueId
 * @since 4.0
 */
public class LongId<T> extends UniqueId<T> {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8542765061773217208L;

	/**
	 * The <code>long</code> of this {@linkplain LongId}.
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
	 * Constructs a new instance of {@linkplain LongId}.
	 *
	 * @param value
	 *            the <code>long</code> of this {@linkplain LongId}.
	 */
	public LongId(final long value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof LongId) {
			final LongId<?> otherId = (LongId<?>) object;
			return value == otherId.value;
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
