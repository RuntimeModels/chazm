/**
 * RequiresRelation.java
 *
 * Created on Mar 3, 2005
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.relation;

import org.models.organization.M;
import org.models.organization.entity.Capability;
import org.models.organization.entity.Role;

/**
 * The {@linkplain RequiresRelation} class is an implementation of the {@linkplain Requires}.
 *
 * @author Christopher Zhong
 * @see Requires
 * @since 1.0
 */
public class RequiresRelation implements Requires {

	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The {@linkplain Role} of this {@linkplain Requires}.
	 */
	private final Role role;

	/**
	 * The {@linkplain Capability} of this {@linkplain Requires}.
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
	 * Constructs a new instance of {@linkplain Requires}.
	 *
	 * @param role
	 *            the {@linkplain Role} of this {@linkplain Requires}.
	 * @param capability
	 *            the {@linkplain Capability} of this {@linkplain Requires}.
	 */
	public RequiresRelation(final Role role, final Capability capability) {
		if (role == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "role"));
		}
		if (capability == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "capability"));
		}
		this.role = role;
		this.capability = capability;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Capability getCapability() {
		return capability;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Requires) {
			final Requires requires = (Requires) object;
			return getRole().equals(requires.getRole()) && getCapability().equals(requires.getCapability());
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
			toString = String.format(STRING_FORMAT, getRole().getId(), getCapability().getId());
		}
		return toString;
	}
}
