/*
 * Role.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.basic.SimpleRole;
import org.models.organization.function.RoleGoodnessFunction;
import org.models.organization.identifier.UniqueIdentifier;
import org.models.organization.relation.Assignment;

/**
 * The <code>Role</code> interface defines the basic role entity of the Organization Model by extending the {@link SimpleRole} interface.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.5.2.11 $, $Date: 2012/01/20 21:24:37 $
 * @see SimpleRole
 * @see RoleGoodnessFunction
 * @since 3.4
 */
public interface Role extends SimpleRole {

	/**
	 * Adds the given <code>SpecificationGoal</code> to the set of <code>SpecificationGoal</code> that this <code>Role</code> achieves.
	 *
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> to add.
	 */
	void addAchieves(SpecificationGoal specificationGoal);

	/**
	 * Returns the set of <code>SpecificationGoal</code> that this <code>Role</code> achieves.
	 *
	 * @return the set of <code>SpecificationGoal</code> that this <code>Role</code> achieves.
	 */
	Set<SpecificationGoal> getAchievesSet();

	/**
	 * Removes the given <code>SpecificationGoal</code> from the set of <code>SpecificationGoal</code> that this <code>Role</code> achieves.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> to remove.
	 */
	void removeAchieves(UniqueIdentifier goalIdentifier);

	/**
	 * Clears the set of <code>SpecificationGoal</code> that this <code>Role</code> achieves.
	 */
	void removeAllAchieves();

	/**
	 * Adds the given <code>Capability</code> to the set of <code>Capability</code> that this <code>Role</code> requires.
	 *
	 * @param capability
	 *            the <code>Capability</code> to be added.
	 */
	void addRequires(Capability capability);

	/**
	 * Adds the given set of <code>Capability</code> to the set of <code>Capability</code> that this <code>Role</code> requires.
	 *
	 * @param capabilities
	 *            the set of <code>Capability</code> to be added.
	 */
	void addRequiresSet(Collection<Capability> capabilities);

	/**
	 * Returns the set of <code>Capability</code> that this <code>Role</code> requires.
	 *
	 * @return the set of <code>Capability</code> that this <code>Role</code> requires.
	 */
	Set<Capability> getRequiresSet();

	/**
	 * Removes the given <code>Capability</code> from the set of <code>Capability</code> that this <code>Role</code> requires.
	 *
	 * @param capabilityIdentifier
	 *            the <code>Capability</code> to be removed.
	 */
	void removeRequires(UniqueIdentifier capabilityIdentifier);

	/**
	 * Clears the set of <code>Capability</code> that this <code>Role</code> requires.
	 */
	void removeAllRequires();

	/**
	 * Adds the given <code>Attribute</code> to the set of <code>Attribute</code> that this <code>Role</code> influences.
	 *
	 * @param attribute
	 *            the <code>Attribute</code> to add.
	 */
	void addNeeds(Attribute attribute);

	/**
	 * Returns the set of <code>Attribute</code> that this <code>Role</code> influences.
	 *
	 * @return the set of <code>Attribute</code> that this <code>Role</code> needs.
	 */
	Set<Attribute> getNeedsSet();

	/**
	 * Removes the given <code>Attribute</code> from the set of <code>Attribute</code> that this <code>Role</code> influences.
	 *
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> to remove.
	 */
	void removeNeeds(UniqueIdentifier attributeIdentifier);

	/**
	 * Clears the set of <code>Attribute</code> that this <code>Role</code> influences.
	 */
	void removeAllNeeds();

	/**
	 * Adds the given <code>Characteristic</code> to the set of <code>Characteristic</code> that this <code>Role</code> contains.
	 *
	 * @param characteristic
	 *            the <code>Characteristic</code> to add.
	 * @param value
	 *            the <code>double</code> value.
	 */
	void addContains(Characteristic characteristic, double value);

	/**
	 * Returns the set of <code>Characteristic</code> that this <code>Role</code> contains.
	 *
	 * @return the set of <code>Characteristic</code> that this <code>Role</code> contains.
	 */
	Set<Characteristic> getContainsSet();

	/**
	 * Returns the value of this <code>Role</code> of the containing <code>Characteristic</code> (from the given <code>UniqueIdentifier</code> ).
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> to retrieve the value.
	 * @return the value of this <code>Role</code> of the containing <code>Characteristic</code>.
	 */
	Double getContainsValue(UniqueIdentifier characteristicIdentifier);

	/**
	 * Sets a new value for this <code>Role</code> of the containing <code>Characteristic</code> (from the given <code>UniqueIdentifier</code> ).
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> to set the new value.
	 * @param value
	 *            the new value of this <code>Role</code> of the containing <code>Characteristic</code>.
	 */
	void setContainsValue(UniqueIdentifier characteristicIdentifier, double value);

	/**
	 * Removes the <code>Characteristic</code> (from the given <code>UniqueIdentifier</code>) from the set of <code>Characteristic</code> that this
	 * <code>Role</code> contains.
	 *
	 * @param characteristicIdentifier
	 */
	void removeContains(UniqueIdentifier characteristicIdentifier);

	/**
	 * Clears the set of <code>Characteristic</code> that this <code>Role</code> contains.
	 */
	void removeAllContains();

	/**
	 * Adds the given <code>PerformanceFunction</code> to the set of <code>PerformanceFunction</code> that is used by this <code>Role</code>.
	 *
	 * @param performanceFunction
	 *            the <code>PerformanceFunction</code> to add.
	 */
	void addUses(PerformanceFunction performanceFunction);

	/**
	 * Returns the <code>PerformanceFunction</code> that is identified by the given <code>UniqueIdentifier</code>.
	 *
	 * @param functionIdentifer
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> to retrieve.
	 * @return the <code>PerformanceFunction</code> if it exists, <code>null</code> otherwise.
	 */
	PerformanceFunction getUses(UniqueIdentifier functionIdentifer);

	/**
	 * Returns the set of <code>PerformanceFunction</code> that is used by this <code>Role</code>.
	 *
	 * @return the set of <code>PerformanceFunction</code> that is used by this <code>Role</code>.
	 */
	Set<PerformanceFunction> getUsesSet();

	/**
	 * Removes the <code>PerformanceFunction</code> (identified by the given <code>UniqueIdentifier</code>) from the set of <code>PerformanceFunction</code>
	 * that is used by this <code>Role</code>.
	 *
	 * @param functionIdentifier
	 *            the <code>PerformanceFunction</code> to remove.
	 */
	void removeUses(UniqueIdentifier functionIdentifier);

	/**
	 * Sets the <code>RoleGoodnessFunction</code> for this <code>Role</code>.
	 *
	 * @param goodnessFunction
	 *            the new <code>RoleGoodnessFunction</code>.
	 */
	void setGoodnessFunction(RoleGoodnessFunction goodnessFunction);

	/**
	 * Determines whether the given <code>SpecificationGoal</code> can be achieved by this <code>Role</code>.
	 *
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> to be checked.
	 * @return <code>true</code> if the <code>Role</code> is able to achieve the given <code>SpecificationGoal</code>, <code>false</code> otherwise.
	 */
	boolean achieves(SpecificationGoal specificationGoal);

	/**
	 * Determines whether the given <code>InstanceGoal</code> can be achieved by this <code>Role</code>.
	 *
	 * @param instanceGoal
	 *            the <code>InstanceGoal</code> to be checked.
	 * @return <code>true</code> if the <code>Role</code> is able to achieve the given <code>InstanceGoal</code>, <code>false</code> otherwise.
	 */
	boolean achieves(InstanceGoal<?> instanceGoal);

	/**
	 * Determines whether the given <code>Capability</code> is required by this <code>Role</code> and returns the result.
	 *
	 * @param capability
	 *            the <code>Capability</code> to be checked.
	 * @return <code>true</code> if the <code>Capability</code> is required, <code>false</code> otherwise.
	 */
	boolean requires(Capability capability);

	/**
	 * Determines whether the given <code>Attribute</code> is influenced by this <code>Role</code> and returns the result.
	 *
	 * @param attribute
	 *            the <code>Attribute</code> to be checked.
	 * @return <code>true</code> if the <code>Attribute</code> is influenced, <code>false</code> otherwise.
	 */
	boolean influences(Attribute attribute);

	/**
	 * Determines whether the given <code>Characteristic</code> is contained by this <code>Role</code> and returns value.
	 *
	 * @param characteristic
	 *            the <code>Characteristic</code> to check.
	 * @return the <code>Double</code> value if the <code>Characteristic</code> is contained, <code>null</code> otherwise.
	 */
	Double contains(Characteristic characteristic);

	/**
	 * Returns the score (<code>0.0</code> &le; score &le; <code>1.0</code>) of how good the given {@link Agent} plays this {@link Role} to achieve the given
	 * {@link InstanceGoal}.
	 *
	 * @param agent
	 *            the {@link Agent} to compute the goodness score.
	 * @param goal
	 *            the {@link InstanceGoal} that may be used in computing the goodness score.
	 * @param assignments
	 *            the {@link Assignment} that may be used in computing the goodness score.
	 * @return the goodness (<code>0.0</code> &le; score &le; <code>1.0</code>) score of how good the given {@link Agent} plays this {@link Role} to achieve the
	 *         given {@link InstanceGoal}. A value of <code>0.0</code> indicates that the {@link Agent} cannot perform the {@link Role} or the
	 *         {@link InstanceGoal} cannot be achieved by the {@link Role}.
	 * @see RoleGoodnessFunction#goodness(Role, Agent, InstanceGoal, Collection)
	 */
	double goodness(Agent agent, InstanceGoal<?> goal, Collection<Assignment> assignments);

}