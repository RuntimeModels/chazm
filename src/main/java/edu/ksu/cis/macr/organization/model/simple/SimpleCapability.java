/*
 * SimpleCapability.java
 * 
 * Created on Jul 30, 2007
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model.simple;

import edu.ksu.cis.macr.organization.model.Capability;
import edu.ksu.cis.macr.organization.model.identifiers.UniqueIdentifier;

/**
 * The <code>SimpleCapability</code> interface defines the simplified version of
 * the {@link Capability} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1 $, $Date: 2009/03/06 15:34:47 $
 * @since 3.4
 */
public interface SimpleCapability {

	/**
	 * Returns the unique <code>UniqueIdentifier</code> representing the
	 * <code>SimpleCapability</code>.
	 * 
	 * @return the unique <code>UniqueIdentifier</code> representing the
	 *         <code>SimpleCapability</code>.
	 */
	UniqueIdentifier getIdentifier();

}