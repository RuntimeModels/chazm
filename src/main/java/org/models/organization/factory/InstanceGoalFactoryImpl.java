/*
 * InstanceGoalFactoryImpl.java
 *
 * Created on Sep 12, 2006
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.factory;

import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.InstanceGoalImpl;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * <code>InstanceGoalFactoryImpl</code> provides a default
 * {@link InstanceGoalFactory} used for creating instances of
 * {@link InstanceGoal}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.2.4.1 $, $Date: 2011/09/19 14:26:36 $
 * @since 3.2
 */
public class InstanceGoalFactoryImpl implements InstanceGoalFactory {

	@Override
	public <ParameterType> InstanceGoal<ParameterType> getInstanceGoal(
			final SpecificationGoal specification,
			final UniqueIdentifier instanceIdentifier,
			final ParameterType parameter) {
		return new InstanceGoalImpl<ParameterType>(specification,
				instanceIdentifier, parameter, false);
	}

}
