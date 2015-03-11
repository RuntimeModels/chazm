/*
 * AgentImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.M;
import org.models.organization.id.UniqueId;

/**
 * The {@link AgentEntity} class is an implementation of the {@link Agent}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Capability
 * @see Role
 * @since 1.0
 */
public class AgentEntity implements Agent {
	/**
	 * The {@linkplain UniqueId} that represents this entity.
	 */
	private final UniqueId<Agent> id;
	/**
	 * Contains contact information on how to contact this agent.
	 */
	private ContactInfo contactInfo = null;

	/**
	 * Constructs a new instance of {@linkplain Agent}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Agent}.
	 */
	public AgentEntity(final UniqueId<Agent> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		this.id = id;
	}

	@Override
	public final UniqueId<Agent> getId() {
		return id;
	}

	@Override
	public final ContactInfo getContactInfo() {
		return contactInfo;
	}

	@Override
	public final void setContactInfo(final ContactInfo contactInfo) {
		if (contactInfo == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "contactInfo"));
		}
		this.contactInfo = contactInfo;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Agent) {
			final Agent agent = (Agent) object;
			return getId().equals(agent.getId());
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