/*
 * Policy.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>Policy</code> interface defines the basic policy entity of the Organization Model.
 *
 * @author Christopher Zhong
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