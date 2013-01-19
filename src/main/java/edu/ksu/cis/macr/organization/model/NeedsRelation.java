/*
 * NeedsRelation.java
 * 
 * Created on May 18, 2010
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model;

/**
 * Represents the needs relation, where a {@link Role} needs an
 * {@link Attribute}.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.2 $, $Date: 2010/06/23 22:15:50 $
 * @since 6.0
 */
public class NeedsRelation {

	/**
	 * Internal <code>String</code> for the formatting of
	 * <code>NeedsRelation</code> class.
	 */
	private static final String STRING_FORMAT = "%s <-> %s";

	/**
	 * The <code>Role</code> of this <code>NeedsRelation</code>.
	 */
	private final Role role;

	/**
	 * The <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	private final Attribute attribute;

	/**
	 * Optimization for hash code computation since it never changes.
	 */
	private int hashCode = 0;

	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	private String toString = null;

	/**
	 * Constructs a new instance of <code>NeedsRelation</code>.
	 * 
	 * @param role
	 *            the <code>Role</code> of this <code>NeedsRelation</code>.
	 * @param attribute
	 *            the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	NeedsRelation(final Role role, final Attribute attribute) {
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format(
					"Parameters (role: %s, attribute: %s) cannot be null",
					role, attribute));
		}
		this.role = role;
		this.attribute = attribute;
	}

	/**
	 * Returns the <code>Role</code> of this <code>NeedsRelation</code>.
	 * 
	 * @return the <code>Role</code> of this <code>NeedsRelation</code>.
	 */
	final Role getRole() {
		return role;
	}

	/**
	 * Returns the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 * 
	 * @return the <code>Attribute</code> of this <code>NeedsRelation</code>.
	 */
	final Attribute getAttribute() {
		return attribute;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof NeedsRelation) {
			final NeedsRelation needsRelation = (NeedsRelation) object;
			return getRole().equals(needsRelation.getRole())
					&& getAttribute().equals(needsRelation.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = getRole().hashCode() << 16 | getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format(STRING_FORMAT, getRole().getIdentifier(),
					getAttribute().getIdentifier());
		}
		return toString;
	}

}
