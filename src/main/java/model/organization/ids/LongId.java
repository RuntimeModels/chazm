/*
 * LongIdentifier.java
 *
 * Created on Feb 7, 2008
 *
 * See License.txt file the license agreement.
 */
package model.organization.ids;

import model.organization.id.AbstractId;
import model.organization.id.UniqueId;

/**
 * The {@linkplain LongId} class extends {@link AbstractId} by using a <code>long</code> as the id.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @see UniqueId
 * @since 4.0
 */
public class LongId<T> extends AbstractId<T> {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8542765061773217208L;

	/**
	 * The <code>long</code> of this {@linkplain LongId}.
	 */
	private final long id;

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
	 * @param type
	 *            the type of the {@linkplain UniqueId}.
	 * @param id
	 *            the <code>long</code> of this {@linkplain LongId}.
	 */
	public LongId(final Class<T> type, final long id) {
		super(type);
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof LongId) {
			final LongId<?> otherId = (LongId<?>) object;
			return id == otherId.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = (int) id;
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.valueOf(id);
		}
		return toString;
	}
}
