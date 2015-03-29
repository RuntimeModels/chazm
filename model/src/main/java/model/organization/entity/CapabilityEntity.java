package model.organization.entity;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class CapabilityEntity extends AbstractEntity<Capability> implements Capability {

	private transient String toString = null;

	@Inject
	CapabilityEntity(@NotNull @Assisted final UniqueId<Capability> id) {
		super(id);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Capability) {
			final Capability capability = (Capability) object;
			return super.equals(capability);
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