/*
 * SimpleCharacteristic.java
 *
 * Created on May 25, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import org.models.organization.entity.Characteristic;
import org.models.organization.identifier.UniqueId;

/**
 * The <code>SimpleCharacteristic</code> interface defines the simplified version of the {@link Characteristic} interface.
 *
 * @author Christopher Zhong
 * @since 6.0
 */
public interface SimpleCharacteristic {

	/**
	 * Returns the <code>UniqueIdentifier</code> representing the <code>SimpleCharacteristic</code>.
	 *
	 * @return the <code>UniqueIdentifier</code> representing the <code>SimpleCharacteristic</code>.
	 */
	UniqueId getIdentifier();

}
