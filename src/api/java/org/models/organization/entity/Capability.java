/*
 * Capability.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.Organization;
import org.models.organization.identifier.Identifiable;
import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain Capability} interface defines a capability entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface Capability extends Identifiable {
	/**
	 * Returns the set of <code>Agent</code> that possesses this <code>Capability</code>.
	 *
	 * @return the set of <code>Agent</code> that possesses this <code>Capability</code>.
	 */
	Set<Agent> getPossessedBySet();

	/**
	 * Returns the score of the given <code>Agent</code> that possesses this <code>Capability</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>Agent</code> to retrieve the score.
	 * @return the score of the given <code>Agent</code> that possesses this <code>Capability</code>.
	 */
	double getPossessedByScore(UniqueId agentIdentifier);

	/**
	 * Returns the set of <code>Role</code> that this <code>Capability</code> requires.
	 *
	 * @return the set of <code>Role</code> that this <code>Capability</code> requires.
	 */
	Set<Role> getRequiredBySet();

}