/*
 * RoleImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Collection;

import org.models.organization.M;
import org.models.organization.function.DefaultRoleGoodnessFunction;
import org.models.organization.function.RoleGoodnessFunction;
import org.models.organization.id.UniqueId;
import org.models.organization.relation.Assignment;

/**
 * The {@linkplain RoleEntity} class is an implementation of the {@link Role}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Capability
 * @see SpecificationGoal
 * @since 1.0
 */
public class RoleEntity implements Role {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Role}.
	 */
	private final UniqueId<Role> id;

	/**
	 * The <code>RoleGoodnessFunction</code> of this <code>Role</code>.
	 */
	private RoleGoodnessFunction goodnessFunction = new DefaultRoleGoodnessFunction();

	/**
	 * Constructs a new instance of {@linkplain Role}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Role}.
	 */
	public RoleEntity(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		this.id = id;
	}

	@Override
	public final UniqueId<Role> getId() {
		return id;
	}

	@Override
	public final void setGoodnessFunction(final RoleGoodnessFunction goodnessFunction) {
		this.goodnessFunction = goodnessFunction;
	}

	@Override
	public final double goodness(final Agent agent, final InstanceGoal<?> goal, final Collection<Assignment> assignments) {
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
