/*
 * DefaultUniqueIdentifierProvider.java
 *
 * Created on Jan 8, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.provider;

import org.models.organization.entity.Agent;
import org.models.organization.entity.Capability;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.StringIdentifier;
import org.models.organization.identifier.UniqueId;

/**
 * The <code>DefaultUniqueIdentifierProvider</code> provides a default implementation of the <code>UniqueIdentifierProvider</code>.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public class DefaultUniqueIdentifierProvider implements UniqueIdentifierProvider {

	@Override
	public UniqueId getUniqueIdentifier(final String identifier, final Class<?> type) {
		if (SpecificationGoal.class.isAssignableFrom(type)) {
			return getUniqueIdentifierForSpecificationGoal(identifier, type);
		}
		if (InstanceGoal.class.isAssignableFrom(type)) {
			return getUniqueIdentifierForInstanceGoal(identifier, type);
		}
		if (Role.class.isAssignableFrom(type)) {
			return getUniqueIdentifierForRole(identifier, type);
		}
		if (Capability.class.isAssignableFrom(type)) {
			return getUniqueIdentifierForCapability(identifier, type);
		}
		if (Agent.class.isAssignableFrom(type)) {
			return getUniqueIdentifierForCapability(identifier, type);
		}
		throw new IllegalArgumentException("Unknown Type");
	}

	/**
	 * This method returns a <code>UniqueIdentifier</code> for the <code>SpecificationGoal</code>.
	 *
	 * @param identifier
	 *            a <code>String</code> that is used to create the <code>UniqueIdentifier</code>.
	 * @param type
	 *            the <code>Class</code> or a sub-<code>Class</code> of the <code>SpecificationGoal</code>.
	 * @return a <code>UniqueIdentifier</code> for the <code>SpecificationGoal</code>.
	 */
	protected UniqueId getUniqueIdentifierForSpecificationGoal(final String identifier, final Class<?> type) {
		return StringIdentifier.getIdentifier(identifier);
	}

	/**
	 * This method returns a <code>UniqueIdentifier</code> for the <code>InstanceGoal</code>.
	 *
	 * @param identifier
	 *            a <code>String</code> that is used to create the <code>UniqueIdentifier</code>.
	 * @param type
	 *            the <code>Class</code> or a sub-<code>Class</code> of the <code>InstanceGoal</code>.
	 * @return a <code>UniqueIdentifier</code> for the <code>InstanceGoal</code> .
	 */
	protected UniqueId getUniqueIdentifierForInstanceGoal(final String identifier, final Class<?> type) {
		return StringIdentifier.getIdentifier(identifier);
	}

	/**
	 * This method returns a <code>UniqueIdentifier</code> for the <code>Role</code>.
	 *
	 * @param identifier
	 *            a <code>String</code> that is used to create the <code>UniqueIdentifier</code>.
	 * @param type
	 *            the <code>Class</code> or a sub-<code>Class</code> of the <code>Role</code>.
	 * @return a <code>UniqueIdentifier</code> for the <code>Role</code>.
	 */
	protected UniqueId getUniqueIdentifierForRole(final String identifier, final Class<?> type) {
		return StringIdentifier.getIdentifier(identifier);
	}

	/**
	 * This method returns a <code>UniqueIdentifier</code> for the <code>Capability</code>.
	 *
	 * @param identifier
	 *            a <code>String</code> that is used to create the <code>UniqueIdentifier</code>.
	 * @param type
	 *            the <code>Class</code> or a sub-<code>Class</code> of the <code>Capability</code>.
	 * @return a <code>UniqueIdentifier</code> for the <code>Capability</code>.
	 */
	protected UniqueId getUniqueIdentifierForCapability(final String identifier, final Class<?> type) {
		return StringIdentifier.getIdentifier(identifier);
	}

	/**
	 * This method returns a <code>UniqueIdentifier</code> for the <code>Agent</code>.
	 *
	 * @param identifier
	 *            a <code>String</code> that is used to create the <code>UniqueIdentifier</code>.
	 * @param type
	 *            the <code>Class</code> or a sub-<code>Class</code> of the <code>Agent</code>.
	 * @return a <code>UniqueIdentifier</code> for the <code>Agent</code>.
	 */
	protected UniqueId getUniqueIdentifierForAgent(final String identifier, final Class<? extends Agent> type) {
		return StringIdentifier.getIdentifier(identifier);
	}

}
