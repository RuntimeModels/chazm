/*
 * SpecificationGoalProvider.java
 *
 * Created on Dec 18, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.parser;

import org.models.organization.entity.SpecificationGoal;
import org.models.organization.parser.xml.XMLParser;

/**
 * The <code>SpecificationGoalProvider</code> interface provides the necessary functionality required for extensions so as to properly interact with the
 * {@link XMLParser}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.2.4.1 $, $Date: 2011/09/19 14:26:41 $
 * @since 4.0
 */
public interface SpecificationGoalProvider {

	/**
	 * Returns the <code>SpecificationGoalType</code> based on the given <code>String</code>.
	 *
	 * @param identifier
	 *            the <code>String</code> identifying the <code>SpecificationGoalType</code>.
	 * @param uniqueIdentifierProvider
	 * @return the <code>SpecificationGoalType</code> if it exists, <code>null</code> otherwise.
	 */
	SpecificationGoal getSpecificationGoal(String identifier, UniqueIdentifierProvider uniqueIdentifierProvider);

}
