/*
 * UniqueIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.id;

import model.organization.NullCheck;

/**
 * The {@linkplain AbstractId} class is an abstract implementation of {@linkplain UniqueId}.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the {@linkplain UniqueId}
 * @since 7.0.0
 */
public abstract class AbstractId<T> implements UniqueId<T>, NullCheck {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 2812867343219462118L;
	private final Class<T> type;

	protected AbstractId(final Class<T> type) {
		checkNotNull(type, "type");
		this.type = type;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();
}