/*
 * Policy.java
 * 
 * Created on Jul 30, 2007
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model;

import edu.ksu.cis.macr.organization.model.identifiers.UniqueIdentifier;

/**
 * The <code>Policy</code> interface defines the basic policy entity of the
 * Organization Model.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1 $, $Date: 2009/03/06 15:34:52 $
 * @since 3.4
 */
public interface Policy {

	/**
	 * Returns the <code>UniqueIdentifier</code> of the <code>Policy</code>.
	 * 
	 * @return the <code>UniqueIdentifier</code> of the <code>Policy</code>.
	 */
	UniqueIdentifier getIdentifier();

}