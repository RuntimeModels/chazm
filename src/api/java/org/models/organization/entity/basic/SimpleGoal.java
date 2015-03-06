/*
 * SimpleGoal.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>SimpleCapability</code> interface defines the simplified version of the {@link SpecificationGoal} and {@link InstanceGoal} interface.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface SimpleGoal {

	/**
	 * Returns the unique <code>UniqueIdentifier</code> representing the <code>SimpleGoal</code>.
	 *
	 * @return the unique <code>UniqueIdentifier</code> representing the <code>SimpleGoal</code>.
	 */
	UniqueIdentifier getIdentifier();

}