/*
 * RoleImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package model.organization.entities;

import java.util.Collection;

import model.organization.NullCheck;
import model.organization.function.DefaultRoleGoodnessFunction;
import model.organization.functions.RoleGoodnessFunction;
import model.organization.id.UniqueId;
import model.organization.relations.Assignment;

/**
 * The {@linkplain RoleEntity} class is an implementation of the {@link Role}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Capability
 * @see SpecificationGoal
 * @since 1.0
 */
public class RoleEntity implements Role, NullCheck {
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
		checkNotNull(id, "id");
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