/*
 * PolicyImpl.java
 * 
 * Created on Feb 25, 2005
 * 
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>Policy</code> class implements the {@link Policy} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.3.2.1 $, $Date: 2011/09/19 14:26:36 $
 * @since 1.0
 */
public abstract class PolicyImpl implements Policy {

	/**
	 * The <code>UniqueIdentifier</code> of the <code>Policy</code>.
	 */
	private final UniqueIdentifier identifier;

	/**
	 * Constructs a new instance of <code>Policy</code>.
	 * 
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> of the <code>Policy</code>.
	 */
	protected PolicyImpl(final UniqueIdentifier identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException(
					"Parameter (identifier) cannot be null");
		}
		this.identifier = identifier;
	}

	@Override
	public final UniqueIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Policy) {
			final Policy policy = (Policy) object;
			return getIdentifier().equals(policy.getIdentifier());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public String toString() {
		return getIdentifier().toString();
	}

}
