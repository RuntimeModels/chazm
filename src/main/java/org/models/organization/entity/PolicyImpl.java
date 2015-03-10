/*
 * PolicyImpl.java
 *
 * Created on Feb 25, 2005
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain PolicyImpl} class is an implementation of the {@link Policy}.
 *
 * @author Christopher Zhong
 * @see Policy
 * @since 1.0
 */
public class PolicyImpl implements Policy {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Policy}.
	 */
	private final UniqueId id;

	/**
	 * Constructs a new instance of {@linkplain Policy}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Policy}.
	 */
	protected PolicyImpl(final UniqueId id) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter (id) cannot be null");
		}
		this.id = id;
	}

	@Override
	public final UniqueId getId() {
		return id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Policy) {
			final Policy policy = (Policy) object;
			return getId().equals(policy.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}
}
