/*
 * SpecificationGoalProviderImpl.java
 *
 * Created on Jan 8, 2008
 *
 * See License.txt file the license agreement.
 */
package model.organization.parser;

import model.organization.entity.SpecificationGoal;
import model.organization.entity.SpecificationGoalImpl;

/**
 * The <code>DefaultSpecificationGoalProvider</code> provides a default
 * implementation for the <code>SpecificationGoalProvider</code>.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.1 $, $Date: 2012/05/02 00:09:15 $
 * @since 4.0
 */
public class DefaultSpecificationGoalProvider implements
		SpecificationGoalProvider {

	@Override
	public SpecificationGoal getSpecificationGoal(final String identifier,
			final UniqueIdentifierProvider uniqueIdentifierProvider) {
		return new SpecificationGoalImpl(
				uniqueIdentifierProvider.getUniqueIdentifier(identifier,
						SpecificationGoal.class));
	}

}
