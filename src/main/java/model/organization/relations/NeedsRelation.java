/**
 * NeedsRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.relations;

import message.M;
import model.organization.entities.Attribute;
import model.organization.entities.Role;

/**
 * The {@linkplain NeedsRelation} class is an implementation of the {@linkplain Needs}.
 *
 * @author Christopher Zhong
 * @see Needs
 * @since 6.0
 */
public class NeedsRelation implements Needs {
	/**
	 * Internal <code>String</code> for the format of the <code>toString</code> method.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The {@linkplain Role} of this {@linkplain Needs}.
	 */
	private final Role role;

	/**
	 * The {@linkplain Attribute} of this {@linkplain Needs}.
	 */
	private final Attribute attribute;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private Integer hashCode = null;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of {@linkplain Needs}.
	 *
	 * @param role
	 *            the {@linkplain Role} of this {@linkplain Needs}.
	 * @param attribute
	 *            the {@linkplain Attribute} of this {@linkplain Needs}.
	 */
	public NeedsRelation(final Role role, final Attribute attribute) {
		if (role == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "role"));
		}
		if (attribute == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "attribute"));
		}
		this.role = role;
		this.attribute = attribute;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Needs) {
			final Needs needs = (Needs) object;
			return getRole().equals(needs.getRole()) && getAttribute().equals(needs.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getId(), getAttribute().getId());
		}
		return toString;
	}
}
