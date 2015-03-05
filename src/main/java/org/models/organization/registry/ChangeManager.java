/*
 * ChangeManager.java
 *
 * Created on Sep 14, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.registry;

import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>ChangeManager</code> interface defines the types of changes that can occur within the organization model.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.2.4.5 $, $Date: 2010/07/30 19:19:41 $
 * @since 4.0
 */
public interface ChangeManager {

	/**
	 * Notification for when a <code>SpecificationGoal</code> is added.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was added.
	 */
	void notifySpecificationGoalAdded(UniqueIdentifier goalIdentifier);

	/**
	 * Notification for when a <code>Specification</code> is removed.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was removed.
	 */
	void notifySpecificationGoalRemoved(UniqueIdentifier goalIdentifier);

	/**
	 * Notification for when a <code>Role</code> is added.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was added.
	 */
	void notifyRoleAdded(UniqueIdentifier roleIdentifier);

	/**
	 * Notification for when a <code>Role</code> is removed.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was removed.
	 */
	void notifyRoleRemoved(UniqueIdentifier roleIdentifier);

	/**
	 * Notification for when an <code>Agent</code> is added.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was added.
	 */
	void notifyAgentAdded(UniqueIdentifier agentIdentifier);

	/**
	 * Notification for when an <code>Agent</code> is removed.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was removed.
	 */
	void notifyAgentRemoved(UniqueIdentifier agentIdentifier);

	/**
	 * Notification for when a <code>Capability</code> is added.
	 *
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was added.
	 */
	void notifyCapabilityAdded(UniqueIdentifier capabilityIdentifier);

	/**
	 * Notification for when a <code>Capability</code> is removed.
	 *
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was removed.
	 */
	void notifyCapabilityRemoved(UniqueIdentifier capabilityIdentifier);

	/**
	 * Notification for when a <code>Policy</code> is added.
	 *
	 * @param policyIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Policy</code> that was added.
	 */
	void notifyPolicyAdded(UniqueIdentifier policyIdentifier);

	/**
	 * Notification for when a <code>Policy</code> is removed.
	 *
	 * @param policyIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Policy</code> that was removed.
	 */
	void notifyPolicyRemoved(UniqueIdentifier policyIdentifier);

	/**
	 * Notification for when an <code>Attribute</code> is added.
	 *
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was added.
	 */
	void notifyAttributeAdded(UniqueIdentifier attributeIdentifier);

	/**
	 * Notification for when an <code>Attribute</code> is removed.
	 *
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was removed.
	 */
	void notifyAttributeRemoved(UniqueIdentifier attributeIdentifier);

	/**
	 * Notification for when an <code>InstanceGoal</code> is added.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code> that was added.
	 * @param parameter
	 *            the <code>Object</code> representing the parameter.
	 */
	void notifyInstanceGoalAdded(UniqueIdentifier goalIdentifier, Object parameter);

	/**
	 * Notification for when an <code>InstanceGoal</code> is removed.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code> that was removed.
	 */
	void notifyInstanceGoalRemoved(UniqueIdentifier goalIdentifier);

	/**
	 * Notification for when an <code>Assignment</code> is added.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was added.
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was added.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was added.
	 */
	void notifyAssignmentAdded(UniqueIdentifier agentIdentifier, UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Notification for when an <code>Assignment</code> is removed.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was removed.
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was removed.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code> that was removed.
	 */
	void notifyAssignmentRemoved(UniqueIdentifier agentIdentifier, UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Notification for when a <code>Task</code> is added.
	 *
	 * @param taskIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Task</code> that was added.
	 */
	void notifyTaskAdded(UniqueIdentifier taskIdentifier);

	/**
	 * Notification for when a <code>Task</code> is removed.
	 *
	 * @param taskIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Task</code> that was removed.
	 */
	void notifyTaskRemoved(UniqueIdentifier taskIdentifier);

	/**
	 * Notification for when a <code>PerformanceFunction</code> is added.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that was added.
	 */
	void notifyPerformanceFunctionAdded(UniqueIdentifier performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>PerformanceFunction</code> is removed.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that was removed.
	 */
	void notifyPerformanceFunctionRemoved(UniqueIdentifier performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>Characteristic</code> is added.
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was added.
	 */
	void notifyCharacteristicAdded(UniqueIdentifier characteristicIdentifier);

	/**
	 * Notification for when a <code>Characteristic</code> is removed.
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was removed.
	 */
	void notifyCharacteristicRemoved(UniqueIdentifier characteristicIdentifier);

	/**
	 * Notification for when an <code>Agent</code> gains a <code>Capability</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was added.
	 * @param score
	 *            the <code>double</code> representing the score of the <code>Capability</code>.
	 */
	void notifyPossessesAdded(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier, double score);

	/**
	 * Notification for when the score of the possesses relation between an <code>Agent</code> and a <code>Capability</code> changes.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was changed.
	 * @param score
	 *            the <code>double</code> representing the score of the <code>Capability</code>.
	 */
	void notifyPossessesChanged(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier, double score);

	/**
	 * Notification for when an <code>Agent</code> loses a <code>Capability</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was removed.
	 */
	void notifyPossessesRemoved(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Notification for when a <code>Role</code> can achieve a <code>SpecificationGoal</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was added.
	 */
	void notifyAchievesAdded(UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Notification for when a <code>Role</code> cannot achieve a <code>SpecificationGoal</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was removed.
	 */
	void notifyAchievesRemoved(UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Notification for when a <code>Role</code> requires a <code>Capability</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was added.
	 */
	void notifyRequiresAdded(UniqueIdentifier roleIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Notification for when a <code>Role</code> no longer requires a <code>Capability</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was removed.
	 */
	void notifyRequiresRemoves(UniqueIdentifier roleIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Notification for when an <code>Agent</code> has an <code>Attribute</code> .
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was added.
	 * @param value
	 *            the <code>double</code> representing the value of the <code>Attribute</code>.
	 */
	void notifyHasAdded(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier, double value);

	/**
	 * Notification for when the value of the has relation between an <code>Agent</code> and an <code>Attribute</code> changes.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was changed.
	 * @param value
	 *            the <code>double</code> representing the value of the <code>Attribute</code>.
	 */
	void notifyHasChanged(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier, double value);

	/**
	 * Notification for when an <code>Agent</code> loses an <code>Attribute</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was removed.
	 */
	void notifyHasRemoved(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Notification for when a <code>Role</code> influences an <code>Attribute</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was influenced.
	 */
	void notifyInfluencesAdded(UniqueIdentifier roleIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Notification for when a <code>Role</code> no longer influences an <code>Attribute</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was influenced.
	 */
	void notifyInfluencesRemoved(UniqueIdentifier roleIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Notification for when a <code>Role</code> is linked to a <code>PerformanceFunction</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that is linked.
	 */
	void notifyLinkedAdded(UniqueIdentifier roleIdentifier, UniqueIdentifier performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>Role</code> is no longer linked to a <code>PerformanceFunction</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that is no longer linked.
	 */
	void notifyLinkedRemoved(UniqueIdentifier roleIdentifier, UniqueIdentifier performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>PerformanceFunction</code> moderates an <code>Attribute</code>.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that is moderated.
	 */
	void notifyModeratesSet(UniqueIdentifier performanceFunctionIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Notification for when a <code>Role</code> contains a <code>Characteristic</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was affected.
	 * @param value
	 *            the <code>double</code> representing the value of the <code>Characteristic</code>.
	 */
	void notifyContainsAdded(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier, double value);

	/**
	 * Notification for when the value of the contains relation between a <code>Role</code> and a <code>Characteristic</code> changes.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was changed.
	 * @param value
	 *            the <code>double</code> representing the value of the <code>Characteristic</code>.
	 */
	void notifyContainsChanged(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier, double value);

	/**
	 * Notification for when a <code>Role</code> no longer contains a <code>Characteristic</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was affected.
	 */
	void notifyContainsRemoved(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier);

}
