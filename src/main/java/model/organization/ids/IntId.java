/*
 * IntegerIdentifier.java
 *
 * Created on Feb 7, 2008
 *
 * See License.txt file the license agreement.
 */
package model.organization.ids;

import model.organization.id.AbstractId;
import model.organization.id.UniqueId;

/**
 * The {@linkplain IntId} extends the {@link AbstractId} by using a <code>int</code> as the id.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @see UniqueId
 * @since 4.0
 */
public class IntId<T> extends AbstractId<T> {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -5052735277036535425L;

	/**
	 * The <code>int</code> of this {@linkplain IntId}.
	 */
	private final int id;

	/**
	 * Optimization for the <code>toString()</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of {@linkplain IntId}.
	 *
	 * @param type
	 *            the type of the {@linkplain IntId}.
	 * @param value
	 *            the <code>int</code> of this {@linkplain IntId}.
	 */
	public IntId(final Class<T> type, final int value) {
		super(type);
		this.id = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof IntId) {
			final IntId<?> otherId = (IntId<?>) object;
			return id == otherId.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.valueOf(id);
		}
		return toString;
	}
}
