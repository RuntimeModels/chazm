/*
 * Characteristic.java
 *
 * Created on May 25, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.entity.basic.SimpleCharacteristic;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>Characteristic</code> interface defines the basic characteristic entity of the Organization Model by extending the {@link SimpleCharacteristic}
 * interface.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.1 $, $Date: 2010/05/25 22:18:33 $
 * @see SimpleCharacteristic
 * @since 6.0
 */
public interface Characteristic extends SimpleCharacteristic {

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
	Double getContainedByValue(UniqueIdentifier roleIdentifier);

}
