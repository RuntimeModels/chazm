/*
 * SpecificationGoalProviderImpl.java
 *
 * Created on Jan 8, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.provider;

import org.models.organization.entity.SpecificationGoal;
import org.models.organization.entity.SpecificationGoalImpl;
import org.models.organization.provider.SpecificationGoalProvider;
import org.models.organization.provider.UniqueIdentifierProvider;

/**
 * The <code>DefaultSpecificationGoalProvider</code> provides a default implementation for the <code>SpecificationGoalProvider</code>.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public class DefaultSpecificationGoalProvider implements SpecificationGoalProvider {

	@Override
	public SpecificationGoal getSpecificationGoal(final String identifier, final UniqueIdentifierProvider uniqueIdentifierProvider) {
		return new SpecificationGoalImpl(uniqueIdentifierProvider.getUniqueIdentifier(identifier, SpecificationGoal.class));
	}

}
