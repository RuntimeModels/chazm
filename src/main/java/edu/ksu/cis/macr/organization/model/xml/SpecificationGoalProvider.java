/*
 * SpecificationGoalProvider.java
 * 
 * Created on Dec 18, 2007
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model.xml;

import edu.ksu.cis.macr.organization.model.SpecificationGoal;

/**
 * The <code>SpecificationGoalProvider</code> interface provides the necessary
 * functionality required for extensions so as to properly interact with the
 * {@link Parser}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.2.4.1 $, $Date: 2011/09/19 14:26:41 $
 * @since 4.0
 */
public interface SpecificationGoalProvider {

	/**
	 * Returns the <code>SpecificationGoalType</code> based on the given
	 * <code>String</code>.
	 * 
	 * @param identifier
	 *            the <code>String</code> identifying the
	 *            <code>SpecificationGoalType</code>.
	 * @param uniqueIdentifierProvider
	 * @return the <code>SpecificationGoalType</code> if it exists,
	 *         <code>null</code> otherwise.
	 */
	SpecificationGoal getSpecificationGoal(String identifier,
			UniqueIdentifierProvider uniqueIdentifierProvider);

}
