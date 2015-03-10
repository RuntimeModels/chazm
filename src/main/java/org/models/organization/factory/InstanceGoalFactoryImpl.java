/*
 * InstanceGoalFactoryImpl.java
 *
 * Created on Sep 12, 2006
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.factory;

import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.InstanceGoalEntity;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.UniqueId;

/**
 * <code>InstanceGoalFactoryImpl</code> provides a default {@link InstanceGoalFactory} used for creating instances of {@link InstanceGoal}.
 *
 * @author Christopher Zhong
 * @since 3.2
 */
public class InstanceGoalFactoryImpl implements InstanceGoalFactory {

	@Override
	public <T> InstanceGoal<T> getInstanceGoal(final SpecificationGoal specification, final UniqueId instanceIdentifier, final T parameter) {
		return new InstanceGoalEntity<T>(specification, instanceIdentifier, parameter);
	}

}
