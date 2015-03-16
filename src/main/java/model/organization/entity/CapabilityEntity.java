package model.organization.entity;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class CapabilityEntity extends AbstractEntity<Capability> implements Capability {

	@Inject
	CapabilityEntity(@NotNull @Assisted final UniqueId<Capability> id) {
		super(id);
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