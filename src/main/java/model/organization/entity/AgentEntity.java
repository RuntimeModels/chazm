package model.organization.entity;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class AgentEntity extends AbstractEntity<Agent> implements Agent {

	private final ContactInfo contactInfo;
	private transient String toString = null;

	@Inject
	AgentEntity(@NotNull @Assisted final UniqueId<Agent> id, @NotNull @Assisted final ContactInfo contactInfo) {
		super(id);
		checkNotNull(contactInfo, "contactInfo");
		this.contactInfo = contactInfo;
	}

	@Override
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Agent) {
			final Agent agent = (Agent) object;
			return super.equals(agent);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s)", getClass().getSimpleName(), getId());
		}
		return toString;
	}

}