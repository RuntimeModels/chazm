/*
 * SimpleCapability.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import org.models.organization.entity.Capability;
import org.models.organization.identifier.UniqueId;

/**
 * The <code>SimpleCapability</code> interface defines the simplified version of the {@link Capability} interface.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface SimpleCapability {

	/**
	 * Returns the unique <code>UniqueIdentifier</code> representing the <code>SimpleCapability</code>.
	 *
	 * @return the unique <code>UniqueIdentifier</code> representing the <code>SimpleCapability</code>.
	 */
	UniqueId getIdentifier();

}