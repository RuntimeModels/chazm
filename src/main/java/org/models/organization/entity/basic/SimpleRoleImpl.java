/*
 * SimpleRoleImpl.java
 *
 * Created on Mar 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import java.util.HashMap;
import java.util.Map;

import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>SimpleRoleImpl</code> class implements the {@link SimpleRole} interface.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.4.2.1 $, $Date: 2011/09/19 14:26:39 $
 * @since 3.2
 */
public class SimpleRoleImpl implements SimpleRole {

	/**
	 * A mapping for <code>UniqueIdentifier</code> that maps to <code>SimpleRole</code> to ensure the uniqueness of <code>SimpleRole</code>.
	 */
	private static final Map<UniqueIdentifier, SimpleRole> uniqueRoles = new HashMap<>();

	/**
	 * The unique <code>UniqueIdentifier</code> of the <code>SimpleRoleImpl</code>.
	 */
	private final UniqueIdentifier identifier;

	/**
	 * Constructs a new instance of <code>SimpleRoleImpl</code>.
	 *
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the <code>SimpleRoleImpl</code>.
	 */
	protected SimpleRoleImpl(final UniqueIdentifier identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException("Parameter (identifier) cannot be null");
		}
		this.identifier = identifier;
	}

	@Override
	public final UniqueIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SimpleRole) {
			final SimpleRole simpleRole = (SimpleRole) object;
			return getIdentifier().equals(simpleRole.getIdentifier());
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

	/**
	 * Returns a <code>SimpleRole</code> instance representing the given <code>UniqueIdentifier</code>.
	 * <p>
	 * If a new <>SimpleRole</code> instance is not required, this method returns the existing <code>SimpleRole</code> instance.
	 *
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the <code>SimpleRole</code> instance.
	 * @return a <code>SimpleRole</code> instance representing the given <code>UniqueIdentifier</code>.
	 */
	public static final SimpleRole createSimpleRole(final UniqueIdentifier identifier) {
		SimpleRole simpleRole = uniqueRoles.get(identifier);
		if (uniqueRoles.get(identifier) == null) {
			simpleRole = new SimpleRoleImpl(identifier);
			uniqueRoles.put(identifier, simpleRole);
		}
		return simpleRole;
	}

}