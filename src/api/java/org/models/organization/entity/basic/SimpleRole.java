/*
 * SimpleRole.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import org.models.organization.entity.Role;
import org.models.organization.identifier.UniqueId;

/**
 * The <code>SimpleRole</code> interface defines the simplified version of the {@link Role} interface.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface SimpleRole {

	/**
	 * Returns the unique <code>UniqueIdentifier</code> representing the <code>SimpleRole</code>.
	 *
	 * @return the unique <code>UniqueIdentifier</code> representing the <code>SimpleRole</code>.
	 */
	UniqueId getIdentifier();

}