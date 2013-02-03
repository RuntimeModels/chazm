/*
 * CharacteristicImpl.java
 * 
 * Created on May 25, 2010
 * 
 * See License.txt file the license agreement.
 */
package model.organization.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.organization.entity.basic.SimpleCharacteristicImpl;
import model.organization.identifier.UniqueIdentifier;
import model.organization.relation.ContainsRelation;

/**
 * The <code></code> class implements the {@link Characteristic} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.2 $, $Date: 2011/09/19 14:26:37 $
 * @since 6.0
 */
public class CharacteristicImpl extends SimpleCharacteristicImpl implements
		Characteristic {

	/**
	 * The set of <code>Role</code> that contains this
	 * <code>Characteristic</code>.
	 */
	private final Map<UniqueIdentifier, ContainsRelation> containedBy = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of <code>CharacteristicImpl</code>.
	 * 
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> representing the
	 *            <code>Characteristic</code>
	 */
	public CharacteristicImpl(final UniqueIdentifier identifier) {
		super(identifier);
	}

	/**
	 * Adds the <code>Role</code> (from the given <code>ContainsRelation</code>)
	 * to the set of <code>Role</code> that contains this
	 * <code>Characteristic</code>.
	 * 
	 * @param containsRelation
	 *            the <code>ContainsRelation</code> to add.
	 */
	final void addContainedBy(final ContainsRelation containsRelation) {
		if (containsRelation == null) {
			throw new IllegalArgumentException(
					"Parameter (containsRelation) cannot be null");
		}
		containedBy.put(containsRelation.getRole().getIdentifier(),
				containsRelation);
	}

	@Override
	public final Set<Role> getContainedBySet() {
		final Set<Role> result = new HashSet<>();
		for (final ContainsRelation containsRelation : containedBy.values()) {
			result.add(containsRelation.getRole());
		}
		return result;
	}

	@Override
	public final Double getContainedByValue(
			final UniqueIdentifier roleIdentifier) {
		if (roleIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (roleIdentifier) cannot be null");
		}
		final ContainsRelation containsRelation = containedBy
				.get(roleIdentifier);
		return containsRelation == null ? null : containsRelation.getValue();
	}

	/**
	 * Removes the given <code>Role</code> from the set of <code>Role</code>
	 * that contains this <code>Characteristic</code>.
	 * 
	 * @param role
	 *            the <code>Role</code> to remove.
	 */
	final void removeContainedBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException(
					"Parameter (role) cannot be null");
		}
		containedBy.remove(role.getIdentifier());
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Characteristic) {
			final Characteristic characteristic = (Characteristic) object;
			return super.equals(characteristic);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
