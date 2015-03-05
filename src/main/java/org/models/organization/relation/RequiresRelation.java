/**
 * RequiresRelation.java
 *
 * Created on Mar 3, 2005
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.entity.Capability;
import org.models.organization.entity.Role;

/**
 * Represents the requires relation, where a {@link Role} requires a {@link Capability}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.2 $, $Date: 2010/05/20 21:16:42 $
 * @see Capability
 * @see Role
 * @since 1.0
 */
public class RequiresRelation {
	/**
	 * Internal <code>String</code> for the format of the <code>IllegalArgumentException</code>.
	 */
	private static final String EXCEPTION_FORMAT = "Parameters (role: %s, capability: %s) cannot be null";

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The <code>Role</code> of this <code>RequiresRelation</code>.
	 */
	private final Role role;

	/**
	 * The <code>Capability</code> of this <code>RequiresRelation</code>.
	 */
	private final Capability capability;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>RequiresRelation</code>.
	 *
	 * @param role
	 *            the <code>Role</code> of this <code>RequiresRelation</code>.
	 * @param capability
	 *            the <code>Capability</code> of this <code>RequiresRelation</code>.
	 */
	public RequiresRelation(final Role role, final Capability capability) {
		if (role == null || capability == null) {
			throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, role, capability));
		}
		this.role = role;
		this.capability = capability;
	}

	/**
	 * Returns the <code>Role</code> of this <code>RequiresRelation</code>.
	 *
	 * @return the <code>Role</code> of this <code>RequiresRelation</code>.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>Capability</code> of this <code>RequiresRelation</code> .
	 *
	 * @return the <code>Capability</code> of this <code>RequiresRelation</code> .
	 */
	public Capability getCapability() {
		return capability;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof RequiresRelation) {
			final RequiresRelation requiresRelation = (RequiresRelation) object;
			return getRole().equals(requiresRelation.getRole()) && getCapability().equals(requiresRelation.getCapability());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getCapability().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getIdentifier(), getCapability().getIdentifier());
		}
		return toString;
	}
}
