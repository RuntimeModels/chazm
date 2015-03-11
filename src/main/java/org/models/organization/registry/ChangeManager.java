/*
 * ChangeManager.java
 *
 * Created on Sep 14, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.registry;

import org.models.organization.id.UniqueId;

/**
 * The <code>ChangeManager</code> interface defines the types of changes that can occur within the organization model.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public interface ChangeManager {

	/**
	 * Notification for when a <code>SpecificationGoal</code> is added.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was added.
	 */
	void notifySpecificationGoalAdded(UniqueId goalIdentifier);

	/**
	 * Notification for when a <code>Specification</code> is removed.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was removed.
	 */
	void notifySpecificationGoalRemoved(UniqueId goalIdentifier);

	/**
	 * Notification for when a <code>Role</code> is added.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was added.
	 */
	void notifyRoleAdded(UniqueId roleIdentifier);

	/**
	 * Notification for when a <code>Role</code> is removed.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was removed.
	 */
	void notifyRoleRemoved(UniqueId roleIdentifier);

	/**
	 * Notification for when an <code>Agent</code> is added.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was added.
	 */
	void notifyAgentAdded(UniqueId agentIdentifier);

	/**
	 * Notification for when an <code>Agent</code> is removed.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was removed.
	 */
	void notifyAgentRemoved(UniqueId agentIdentifier);

	/**
	 * Notification for when a <code>Capability</code> is added.
	 *
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was added.
	 */
	void notifyCapabilityAdded(UniqueId capabilityIdentifier);

	/**
	 * Notification for when a <code>Capability</code> is removed.
	 *
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was removed.
	 */
	void notifyCapabilityRemoved(UniqueId capabilityIdentifier);

	/**
	 * Notification for when a <code>Policy</code> is added.
	 *
	 * @param policyIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Policy</code> that was added.
	 */
	void notifyPolicyAdded(UniqueId policyIdentifier);

	/**
	 * Notification for when a <code>Policy</code> is removed.
	 *
	 * @param policyIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Policy</code> that was removed.
	 */
	void notifyPolicyRemoved(UniqueId policyIdentifier);

	/**
	 * Notification for when an <code>Attribute</code> is added.
	 *
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was added.
	 */
	void notifyAttributeAdded(UniqueId attributeIdentifier);

	/**
	 * Notification for when an <code>Attribute</code> is removed.
	 *
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was removed.
	 */
	void notifyAttributeRemoved(UniqueId attributeIdentifier);

	/**
	 * Notification for when an <code>InstanceGoal</code> is added.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code> that was added.
	 * @param parameter
	 *            the <code>Object</code> representing the parameter.
	 */
	void notifyInstanceGoalAdded(UniqueId goalIdentifier, Object parameter);

	/**
	 * Notification for when an <code>InstanceGoal</code> is removed.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code> that was removed.
	 */
	void notifyInstanceGoalRemoved(UniqueId goalIdentifier);

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
	void notifyAssignmentAdded(UniqueId agentIdentifier, UniqueId roleIdentifier, UniqueId goalIdentifier);

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
	void notifyAssignmentRemoved(UniqueId agentIdentifier, UniqueId roleIdentifier, UniqueId goalIdentifier);

	/**
	 * Notification for when a <code>Task</code> is added.
	 *
	 * @param taskIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Task</code> that was added.
	 */
	void notifyTaskAdded(UniqueId taskIdentifier);

	/**
	 * Notification for when a <code>Task</code> is removed.
	 *
	 * @param taskIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Task</code> that was removed.
	 */
	void notifyTaskRemoved(UniqueId taskIdentifier);

	/**
	 * Notification for when a <code>PerformanceFunction</code> is added.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that was added.
	 */
	void notifyPerformanceFunctionAdded(UniqueId performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>PerformanceFunction</code> is removed.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that was removed.
	 */
	void notifyPerformanceFunctionRemoved(UniqueId performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>Characteristic</code> is added.
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was added.
	 */
	void notifyCharacteristicAdded(UniqueId characteristicIdentifier);

	/**
	 * Notification for when a <code>Characteristic</code> is removed.
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was removed.
	 */
	void notifyCharacteristicRemoved(UniqueId characteristicIdentifier);

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
	void notifyPossessesAdded(UniqueId agentIdentifier, UniqueId capabilityIdentifier, double score);

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
	void notifyPossessesChanged(UniqueId agentIdentifier, UniqueId capabilityIdentifier, double score);

	/**
	 * Notification for when an <code>Agent</code> loses a <code>Capability</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was removed.
	 */
	void notifyPossessesRemoved(UniqueId agentIdentifier, UniqueId capabilityIdentifier);

	/**
	 * Notification for when a <code>Role</code> can achieve a <code>SpecificationGoal</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was added.
	 */
	void notifyAchievesAdded(UniqueId roleIdentifier, UniqueId goalIdentifier);

	/**
	 * Notification for when a <code>Role</code> cannot achieve a <code>SpecificationGoal</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code> that was removed.
	 */
	void notifyAchievesRemoved(UniqueId roleIdentifier, UniqueId goalIdentifier);

	/**
	 * Notification for when a <code>Role</code> requires a <code>Capability</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was added.
	 */
	void notifyRequiresAdded(UniqueId roleIdentifier, UniqueId capabilityIdentifier);

	/**
	 * Notification for when a <code>Role</code> no longer requires a <code>Capability</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Capability</code> that was removed.
	 */
	void notifyRequiresRemoves(UniqueId roleIdentifier, UniqueId capabilityIdentifier);

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
	void notifyHasAdded(UniqueId agentIdentifier, UniqueId attributeIdentifier, double value);

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
	void notifyHasChanged(UniqueId agentIdentifier, UniqueId attributeIdentifier, double value);

	/**
	 * Notification for when an <code>Agent</code> loses an <code>Attribute</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Agent</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was removed.
	 */
	void notifyHasRemoved(UniqueId agentIdentifier, UniqueId attributeIdentifier);

	/**
	 * Notification for when a <code>Role</code> influences an <code>Attribute</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was influenced.
	 */
	void notifyInfluencesAdded(UniqueId roleIdentifier, UniqueId attributeIdentifier);

	/**
	 * Notification for when a <code>Role</code> no longer influences an <code>Attribute</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that was influenced.
	 */
	void notifyInfluencesRemoved(UniqueId roleIdentifier, UniqueId attributeIdentifier);

	/**
	 * Notification for when a <code>Role</code> is linked to a <code>PerformanceFunction</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that is linked.
	 */
	void notifyLinkedAdded(UniqueId roleIdentifier, UniqueId performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>Role</code> is no longer linked to a <code>PerformanceFunction</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that is no longer linked.
	 */
	void notifyLinkedRemoved(UniqueId roleIdentifier, UniqueId performanceFunctionIdentifier);

	/**
	 * Notification for when a <code>PerformanceFunction</code> moderates an <code>Attribute</code>.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>PerformanceFunction</code> that was affected.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Attribute</code> that is moderated.
	 */
	void notifyModeratesAdded(UniqueId performanceFunctionIdentifier, UniqueId attributeIdentifier);

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
	void notifyContainsAdded(UniqueId roleIdentifier, UniqueId characteristicIdentifier, double value);

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
	void notifyContainsChanged(UniqueId roleIdentifier, UniqueId characteristicIdentifier, double value);

	/**
	 * Notification for when a <code>Role</code> no longer contains a <code>Characteristic</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> that was affected.
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> that was affected.
	 */
	void notifyContainsRemoved(UniqueId roleIdentifier, UniqueId characteristicIdentifier);

}
