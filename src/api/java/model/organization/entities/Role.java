/*
 * Role.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.entities;

import java.util.Collection;

import model.organization.Organization;
import model.organization.functions.RoleGoodnessFunction;
import model.organization.id.Identifiable;
import model.organization.relations.Assignment;

/**
 * The {@linkplain Role} interface defines a role entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @see RoleGoodnessFunction
 * @since 3.4
 */
public interface Role extends Identifiable<Role> {
	/**
	 * Sets the <code>RoleGoodnessFunction</code> for this <code>Role</code>.
	 *
	 * @param goodnessFunction
	 *            the new <code>RoleGoodnessFunction</code>.
	 */
	void setGoodnessFunction(RoleGoodnessFunction goodnessFunction);

	/**
	 * Returns the score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how good the given {@link Agent} plays this {@link Role} to achieve the given
	 * {@link InstanceGoal}.
	 *
	 * @param agent
	 *            the {@link Agent} to compute the goodness score.
	 * @param goal
	 *            the {@link InstanceGoal} that may be used in computing the goodness score.
	 * @param assignments
	 *            the {@link Assignment} that may be used in computing the goodness score.
	 * @return the goodness (<code>0.0</code> &le; score &le; <code>1.0</code>) score of how good the given {@link Agent} plays this {@link Role} to achieve the
	 *         given {@link InstanceGoal}. A value of <code>0.0</code> indicates that the {@link Agent} cannot perform the {@link Role} or the
	 *         {@link InstanceGoal} cannot be achieved by the {@link Role}.
	 * @see RoleGoodnessFunction#goodness(Role, Agent, InstanceGoal, Collection)
	 */
	double goodness(Agent agent, InstanceGoal goal, Collection<Assignment> assignments);

}