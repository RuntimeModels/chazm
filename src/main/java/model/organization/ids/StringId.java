/*
 * StringIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.ids;

import message.M;
import model.organization.id.AbstractId;
import model.organization.id.UniqueId;

/**
 * The {@linkplain StringId} class extends the {@link AbstractId} by using a {@link String} as the id.
 * <p>
 * This {@linkplain StringId} class is provided for easy of use only. The {@link #equals(Object)} method defaults to the {@link String#equals(Object)} which is
 * linear in the length of {@linkplain String} . If performance is an issue, it is recommended that the {@link #equals(Object)} method is overridden to provide
 * a constant time complexity.
 *
 * @author Christopher Zhong, Scott Harmon
 * @param <T>
 *            the type of the {@linkplain UniqueId}.
 * @see UniqueId
 * @since 4.0
 */
public class StringId<T> extends AbstractId<T> {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 522905742372399827L;

	/**
	 * The {@linkplain String} of this {@linkplain StringId}.
	 */
	private final String id;

	/**
	 * Constructs a new instance of {@linkplain StringId}.
	 *
	 * @param type
	 *            the type of the {@linkplain StringId}.
	 * @param id
	 *            the {@linkplain String} of the {@linkplain StringId}.
	 */
	public StringId(final Class<T> type, final String id) {
		super(type);
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof StringId) {
			final StringId<?> otherId = (StringId<?>) object;
			return id.equals(otherId.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return id;
	}
}
