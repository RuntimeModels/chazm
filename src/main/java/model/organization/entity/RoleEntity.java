package model.organization.entity;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.function.DefaultRoleGoodnessFunction;
import model.organization.functions.RoleGoodnessFunction;
import model.organization.id.UniqueId;
import model.organization.relation.Assignment;

import com.google.inject.assistedinject.Assisted;

class RoleEntity extends AbstractEntity<Role> implements Role {

	private RoleGoodnessFunction goodnessFunction = new DefaultRoleGoodnessFunction(); // TODO make it organization centric

	@Inject
	RoleEntity(@NotNull @Assisted final UniqueId<Role> id) {
		super(id);
	}

	@Override
	public final void setGoodnessFunction(final RoleGoodnessFunction goodnessFunction) {
		this.goodnessFunction = goodnessFunction;
	}

	@Override
	public final double goodness(final Agent agent, final InstanceGoal goal, final Collection<Assignment> assignments) {
		return goodnessFunction.goodness(this, agent, goal, assignments);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Role) {
			final Role role = (Role) object;
			return getId().equals(role.getId());
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
