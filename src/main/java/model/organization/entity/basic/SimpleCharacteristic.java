/*
 * SimpleCharacteristic.java
 * 
 * Created on May 25, 2010
 * 
 * See License.txt file the license agreement.
 */
package model.organization.entity.basic;

import model.organization.entity.Characteristic;
import model.organization.identifier.UniqueIdentifier;

/**
 * The <code>SimpleCharacteristic</code> interface defines the simplified
 * version of the {@link Characteristic} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.1 $, $Date: 2010/05/25 22:17:28 $
 * @since 6.0
 */
public interface SimpleCharacteristic {

	/**
	 * Returns the <code>UniqueIdentifier</code> representing the
	 * <code>SimpleCharacteristic</code>.
	 * 
	 * @return the <code>UniqueIdentifier</code> representing the
	 *         <code>SimpleCharacteristic</code>.
	 */
	UniqueIdentifier getIdentifier();

}
