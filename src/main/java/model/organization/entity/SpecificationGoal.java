/*
 * SpecificationGoal.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.entity;

import java.util.Set;

import edu.ksu.cis.macr.organization.model.identifiers.UniqueIdentifier;
import edu.ksu.cis.macr.organization.model.simple.SimpleGoal;

/**
 * The <code>SpecificationGoal</code> interface defines the static goal entity
 * of the Organization Model by extending the {@link SimpleGoal} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.4.2.2 $, $Date: 2011/09/07 17:37:21 $
 * @since 3.4
 */
public interface SpecificationGoal extends SimpleGoal {

	/**
	 * Returns the set of <code>Role</code> that achieves this
	 * <code>SpecificationGoal</code>.
	 * 
	 * @return the set of <code>Role</code> that achieves this
	 *         <code>SpecificationGoal</code>.
	 */
	Set<Role> getAchievedBySet();

	/**
	 * Creates an instance of this <code>SpecificationGoal</code>.
	 * 
	 * @param <ParameterType>
	 *            the type of the parameter for the <code>InstanceGoal</code>.
	 * @param instanceIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the instance of
	 *            the <code>InstanceGoal</code> for the
	 *            <code>SpecificationGoal</code>.
	 * @param parameter
	 *            the parameter of the <code>InstanceGoal</code>.
	 * @return the <code>InstanceGoal</code>.
	 */
	<ParameterType> InstanceGoal<ParameterType> getInstanceGoal(
			UniqueIdentifier instanceIdentifier, ParameterType parameter);

}