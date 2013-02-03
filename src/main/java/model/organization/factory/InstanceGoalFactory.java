/*
 * InstanceGoalFactory.java
 * 
 * Created on Sep 12, 2006
 * 
 * See License.txt file the license agreement.
 */
package model.organization.factory;

import model.organization.entity.InstanceGoal;
import model.organization.entity.SpecificationGoal;
import model.organization.identifier.UniqueIdentifier;

/**
 * The <code>InstanceGoalFactory</code> interface provides a standard way to
 * instantiate {@link InstanceGoal} in the Organization Model.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.2 $, $Date: 2009/03/31 10:07:08 $
 * @since 3.2
 */
public interface InstanceGoalFactory {

	/**
	 * Creates an instance of a <code>InstanceGoal</code> based on the given
	 * <code>SpecificationGoal</code>.
	 * 
	 * @param <ParameterType>
	 *            the type of the parameter for the <code>InstanceGoal</code>.
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> that is going to create the
	 *            <code>InstanceGoal</code>.
	 * @param instanceIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the
	 *            <code>InstanceGoal</code> for the given
	 *            <code>SpecificationGoal</code>.
	 * @param parameter
	 *            the parameter of the <code>InstanceGoal</code> .
	 * @return an instance of <code>InstanceGoal</code>.
	 */
	public <ParameterType> InstanceGoal<ParameterType> getInstanceGoal(
			SpecificationGoal specificationGoal,
			UniqueIdentifier instanceIdentifier, ParameterType parameter);

}
