/*
 * Characteristic.java
 *
 * Created on May 25, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.Organization;
import org.models.organization.identifier.Identifiable;
import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain Characteristic} interface defines the characteristic entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public interface Characteristic extends Identifiable {
	/**
	 * Returns the set of <code>Role</code> that contains this <code>Characteristic</code>.
	 *
	 * @return the set of <code>Role</code> that contains this <code>Characteristic</code>.
	 */
	Set<Role> getContainedBySet();

	/**
	 * Returns the value of the <code>Role</code> (from the given <code>UniqueIdentifier</code>) that contains this <code>Characteristic</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> to retrieve the value.
	 * @return the value if it exists, <code>null</code> otherwise.
	 */
	Double getContainedByValue(UniqueId roleIdentifier);

}
