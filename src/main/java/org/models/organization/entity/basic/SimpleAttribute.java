/*
 * SimpleAttribute.java
 * 
 * Created on Oct 15, 2009
 * 
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import org.models.organization.entity.Attribute;
import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>SimpleAttribute</code> interface defines the simplified version of
 * the {@link Attribute} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.2 $, $Date: 2010/05/20 21:13:33 $
 * @since 5.0
 */
public interface SimpleAttribute {

	/**
	 * Returns the unique <code>UniqueIdentifier</code> representing the
	 * <code>SimpleAttribute</code>.
	 * 
	 * @return the unique <code>UniqueIdentifier</code> representing the
	 *         <code>SimpleAttribute</code>.
	 */
	UniqueIdentifier getIdentifier();

}
