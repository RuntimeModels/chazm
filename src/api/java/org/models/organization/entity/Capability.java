/*
 * Capability.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.entity.basic.SimpleCapability;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>Capability</code> interface defines the basic capability entity of the Organization Model by extending {@link SimpleCapability}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.4.2.1 $, $Date: 2010/05/20 21:21:08 $
 * @since 3.4
 */
public interface Capability extends SimpleCapability {

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
	double getPossessedByScore(UniqueIdentifier agentIdentifier);

	/**
	 * Returns the set of <code>Role</code> that this <code>Capability</code> requires.
	 *
	 * @return the set of <code>Role</code> that this <code>Capability</code> requires.
	 */
	Set<Role> getRequiredBySet();

}