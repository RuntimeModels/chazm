/*
 * CapabilityImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.M;
import org.models.organization.id.UniqueId;

/**
 * The {@link CapabilityEntity} class is an implementation of the {@link Capability}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Capability
 * @since 1.0
 */
public class CapabilityEntity implements Capability {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Capability}.
	 */
	private final UniqueId<Capability> id;

	/**
	 * Constructs a new instance of {@linkplain Capability}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Capability}.
	 */
	public CapabilityEntity(final UniqueId<Capability> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		this.id = id;
	}

	@Override
	public final UniqueId<Capability> getId() {
		return id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Capability) {
			final Capability capability = (Capability) object;
			return getId().equals(capability.getId());
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