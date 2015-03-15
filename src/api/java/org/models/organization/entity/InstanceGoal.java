/*
 * InstanceGoal.java
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
 * The {@linkplain InstanceGoal} interface defines the instance goal, which is a concrete instantiation of a {@link SpecificationGoal}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface InstanceGoal extends Identifiable<InstanceGoal> {
	/**
	 * The {@linkplain P} interface defines the parameter of an {@linkplain InstanceGoal}.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	interface P {
	}

	/**
	 * Returns the {@linkplain UniqueId} that represents this instance portion of this {@linkplain InstanceGoal}.
	 *
	 * @return the {@linkplain UniqueId} that represents this instance portion of this {@linkplain InstanceGoal}.
	 */
	UniqueId<InstanceGoal> getInstanceId();

	/**
	 * Returns the {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
	 *
	 * @return the {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
	 */
	SpecificationGoal getSpecificationGoal();

	/**
	 * Returns the parameter(s) of this {@linkplain InstanceGoal}.
	 *
	 * @return the parameter(s) of this {@linkplain InstanceGoal}.
	 */
	P getParameter();
}