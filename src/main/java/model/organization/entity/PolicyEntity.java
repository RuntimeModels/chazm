package model.organization.entity;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class PolicyEntity extends AbstractEntity<Policy> implements Policy {

	@Inject
	PolicyEntity(@NotNull @Assisted final UniqueId<Policy> id) {
		super(id);
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
