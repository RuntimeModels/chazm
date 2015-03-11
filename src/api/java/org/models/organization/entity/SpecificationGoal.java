/*
 * SpecificationGoal.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.Organization;
import org.models.organization.id.Identifiable;
import org.models.organization.id.UniqueId;

/**
 * The {@linkplain SpecificationGoal} interface defines the specification goal entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface SpecificationGoal extends Identifiable<SpecificationGoal> {
	/**
	 * Creates an instance of this <code>SpecificationGoal</code>.
	 *
	 * @param <ParameterType>
	 *            the type of the parameter for the <code>InstanceGoal</code>.
	 * @param instanceIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the instance of the <code>InstanceGoal</code> for the <code>SpecificationGoal</code>.
	 * @param parameter
	 *            the parameter of the <code>InstanceGoal</code>.
	 * @return the <code>InstanceGoal</code>.
	 */
	<ParameterType> InstanceGoal<ParameterType> getInstanceGoal(UniqueId instanceIdentifier, ParameterType parameter);

}