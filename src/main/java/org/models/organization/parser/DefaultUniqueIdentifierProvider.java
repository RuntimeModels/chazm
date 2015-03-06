/*
 * DefaultUniqueIdentifierProvider.java
 *
 * Created on Jan 8, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.parser;

import org.models.organization.entity.Agent;
import org.models.organization.entity.Capability;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.StringIdentifier;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>DefaultUniqueIdentifierProvider</code> provides a default implementation of the <code>UniqueIdentifierProvider</code>.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.1 $, $Date: 2011/09/19 14:26:41 $
 * @since 4.0
 */
public class DefaultUniqueIdentifierProvider implements UniqueIdentifierProvider {

	@Override
	public UniqueIdentifier getUniqueIdentifier(final String identifier, final Class<?> type) {
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
	protected UniqueIdentifier getUniqueIdentifierForSpecificationGoal(final String identifier, final Class<?> type) {
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
	protected UniqueIdentifier getUniqueIdentifierForInstanceGoal(final String identifier, final Class<?> type) {
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
	protected UniqueIdentifier getUniqueIdentifierForRole(final String identifier, final Class<?> type) {
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
	protected UniqueIdentifier getUniqueIdentifierForCapability(final String identifier, final Class<?> type) {
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
	protected UniqueIdentifier getUniqueIdentifierForAgent(final String identifier, final Class<? extends Agent> type) {
		return StringIdentifier.getIdentifier(identifier);
	}

}
