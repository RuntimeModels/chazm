/*
 * UniqueIdentifierProvider.java
 *
 * Created on Dec 19, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.provider;

import org.models.organization.identifier.UniqueId;

/**
 * The <code>UniqueIdentifierProvider</code> interface defines the necessary functionality for using user-defined {@link UniqueId} when parsing files.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public interface UniqueIdentifierProvider {

	/**
	 * Returns a <code>UniqueIdentifier</code> based on the given <code>String</code> and for the given <code>Class</code>.
	 * <p>
	 * Currently, the entities are <code>Agent</code>, <code>Capability</code>, <code>InstanceGoal</code>, <code>Policy</code>, <code>Role</code>, and
	 * <code>SpecificationGoal</code>.
	 *
	 * @param identifier
	 *            a <code>String</code> that is used to create the <code>UniqueIdentifier</code>.
	 * @param type
	 *            the <code>Class</code> or a sub-<code>Class</code> of one of the entities of the Organization Model.
	 * @return a <code>UniqueIdentifier</code> for the given <code>String</code> .
	 */
	UniqueId getUniqueIdentifier(String identifier, Class<?> type);

}
