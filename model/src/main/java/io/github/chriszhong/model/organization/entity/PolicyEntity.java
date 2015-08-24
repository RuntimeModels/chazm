package io.github.chriszhong.model.organization.entity;

import io.github.chriszhong.model.organization.id.UniqueId;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

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
			return super.equals(policy);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
