/*
 * ClassIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.ids;

import model.organization.id.AbstractId;
import model.organization.id.UniqueId;

/**
 * The {@linkplain ClassId} extends the {@link AbstractId} by using a {@linkplain Class} as the id.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @see UniqueId
 * @since 4.0
 */
public class ClassId<T> extends AbstractId<T> {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 5751013993559212419L;

	/**
	 * The {@linkplain Class} of this {@linkplain ClassId}.
	 */
	private final Class<?> id;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private transient Integer hashCode = null;

	/**
	 * Optimization for the <code>toString()</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of {@linkplain ClassId}.
	 *
	 * @param type
	 *            the type of the {@linkplain ClassId}.
	 * @param id
	 *            the {@linkplain Class} of this {@linkplain ClassId}.
	 */
	public ClassId(final Class<T> type, final Class<?> id) {
		super(type);
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof ClassId) {
			final ClassId<?> classIdentifier = (ClassId<?>) object;
			return id.equals(classIdentifier.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = id.hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = id.getName();
		}
		return toString;
	}
}
