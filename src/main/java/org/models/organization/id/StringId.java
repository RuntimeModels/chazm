/*
 * StringIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.id;

import org.models.organization.M;
import org.models.organization.id.UniqueId;

/**
 * The {@linkplain StringId} class extends the {@link UniqueId} by using a {@link String} as the id.
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
public class StringId<T> extends UniqueId<T> {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 522905742372399827L;

	/**
	 * The {@linkplain String} of this {@linkplain StringId}.
	 */
	private final String string;

	/**
	 * Constructs a new instance of {@linkplain StringId}.
	 *
	 * @param id
	 *            the {@linkplain String} of the {@linkplain StringId}.
	 */
	public StringId(final String id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		this.string = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof StringId) {
			final StringId<?> otherId = (StringId<?>) object;
			return string.equals(otherId.string);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return string.hashCode();
	}

	@Override
	public String toString() {
		return string;
	}
}
