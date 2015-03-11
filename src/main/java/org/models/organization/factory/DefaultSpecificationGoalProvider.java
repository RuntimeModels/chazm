/*
 * SpecificationGoalProviderImpl.java
 *
 * Created on Jan 8, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.factory;

import org.models.organization.entity.SpecificationGoal;
import org.models.organization.entity.SpecificationGoalEntity;
import org.models.organization.factory.SpecificationGoalProvider;
import org.models.organization.factory.UniqueIdFactory;

/**
 * The <code>DefaultSpecificationGoalProvider</code> provides a default implementation for the <code>SpecificationGoalProvider</code>.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public class DefaultSpecificationGoalProvider implements SpecificationGoalProvider {

	@Override
	public SpecificationGoal getSpecificationGoal(final String identifier, final UniqueIdFactory uniqueIdFactory) {
		return new SpecificationGoalEntity(uniqueIdFactory.getUniqueId(identifier, SpecificationGoal.class));
	}

}
