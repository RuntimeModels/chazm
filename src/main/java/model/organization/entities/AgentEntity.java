/*
 * AgentImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package model.organization.entities;

import model.organization.NullCheck;
import model.organization.id.UniqueId;

/**
 * The {@link AgentEntity} class is an implementation of the {@link Agent}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Capability
 * @see Role
 * @since 1.0
 */
public class AgentEntity implements Agent, NullCheck {
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
		checkNotNull(id, "id");
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
		checkNotNull(contactInfo, "contactInfo");
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