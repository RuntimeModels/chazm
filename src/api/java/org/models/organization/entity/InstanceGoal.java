/*
 * InstanceGoal.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.entity.basic.SimpleGoal;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>InstanceGoal</code> interface defines the concrete instance of a {@link SpecificationGoal} that belongs in the active goal set.
 *
 * @author Christopher Zhong
 * @param <ParameterType>
 *            the sub-type of the parameter for the <code>InstanceGoal</code>.
 * @since 3.4
 */
public interface InstanceGoal<ParameterType> extends SimpleGoal {

	/**
	 * Returns the <code>SpecificationGoal</code> that this <code>InstanceGoal</code> is based on.
	 *
	 * @return the <code>SpecificationGoal</code> that this <code>InstanceGoal</code> is based on.
	 */
	SpecificationGoal getSpecificationGoal();

	/**
	 * Returns the <code>UniqueIdentifier</code> identifying the <code>SpecificationGoal</code> that this <code>InstanceGoal</code> is based on.
	 *
	 * @return the <code>UniqueIdentifier</code> identifying the <code>SpecificationGoal</code> that this <code>InstanceGoal</code> is based on.
	 */
	UniqueIdentifier getSpecificationIdentifier();

	/**
	 * Returns the <code>UniqueIdentifier</code> identifying this <code>InstanceGoal</code> with respect to the <code>SpecificationGoal</code>.
	 *
	 * @return the <code>UniqueIdentifier</code> identifying this <code>InstanceGoal</code> with respect the <code>SpecificationGoal</code> that this
	 *         <code>InstanceGoal</code> is based on.
	 */
	UniqueIdentifier getInstanceIdentifier();

	/**
	 * Returns the parameter(s) of this <code>InstanceGoal</code>.
	 *
	 * @return the parameter(s) of this <code>InstanceGoal</code>.
	 */
	ParameterType getParameter();

	/**
	 * Returns the set of <code>Role</code> that achieves this <code>SpecificationGoal</code>.
	 *
	 * @return the set of <code>Role</code> that achieves this <code>SpecificationGoal</code>.
	 * @see SpecificationGoal#getAchievedBySet()
	 */
	Set<Role> getAchievedBySet();

}