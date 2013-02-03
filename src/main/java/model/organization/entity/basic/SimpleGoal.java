/*
 * SimpleGoal.java
 * 
 * Created on Jul 30, 2007
 * 
 * See License.txt file the license agreement.
 */
package model.organization.entity.basic;

import model.organization.entity.InstanceGoal;
import model.organization.entity.SpecificationGoal;
import model.organization.identifier.UniqueIdentifier;

/**
 * The <code>SimpleCapability</code> interface defines the simplified version of
 * the {@link SpecificationGoal} and {@link InstanceGoal} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1 $, $Date: 2009/03/06 15:34:47 $
 * @since 3.4
 */
public interface SimpleGoal {

	/**
	 * Returns the unique <code>UniqueIdentifier</code> representing the
	 * <code>SimpleGoal</code>.
	 * 
	 * @return the unique <code>UniqueIdentifier</code> representing the
	 *         <code>SimpleGoal</code>.
	 */
	UniqueIdentifier getIdentifier();

}