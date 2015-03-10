/*
 * CharacteristicImpl.java
 *
 * Created on May 25, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.models.organization.identifier.UniqueId;
import org.models.organization.relation.Contains;
import org.models.organization.relation.ContainsRelation;

/**
 * The {@link CharacteristicEntity} class is an implementation of the {@link Characteristic}.
 *
 * @author Christopher Zhong
 * @see Characteristic
 * @since 6.0
 */
public class CharacteristicEntity implements Characteristic {
	/**
	 * The {@linkplain UniqueId} that represents this {@link Characteristic}.
	 */
	private final UniqueId id;

	/**
	 * The set of <code>Role</code> that contains this <code>Characteristic</code>.
	 */
	private final Map<UniqueId, ContainsRelation> containedBy = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of {@link Characteristic}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@link Characteristic}.
	 */
	public CharacteristicEntity(final UniqueId id) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter (id) cannot be null");
		}
		this.id = id;
	}

	@Override
	public final UniqueId getId() {
		return id;
	}

	/**
	 * Adds the <code>Role</code> (from the given <code>ContainsRelation</code>) to the set of <code>Role</code> that contains this <code>Characteristic</code>.
	 *
	 * @param containsRelation
	 *            the <code>ContainsRelation</code> to add.
	 */
	final void addContainedBy(final ContainsRelation containsRelation) {
		if (containsRelation == null) {
			throw new IllegalArgumentException("Parameter (containsRelation) cannot be null");
		}
		containedBy.put(containsRelation.getRole().getId(), containsRelation);
	}

	@Override
	public final Set<Role> getContainedBySet() {
		final Set<Role> result = new HashSet<>();
		for (final Contains containsRelation : containedBy.values()) {
			result.add(containsRelation.getRole());
		}
		return result;
	}

	@Override
	public final Double getContainedByValue(final UniqueId roleIdentifier) {
		if (roleIdentifier == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifier) cannot be null");
		}
		final Contains containsRelation = containedBy.get(roleIdentifier);
		return containsRelation == null ? null : containsRelation.getValue();
	}

	/**
	 * Removes the given <code>Role</code> from the set of <code>Role</code> that contains this <code>Characteristic</code>.
	 *
	 * @param role
	 *            the <code>Role</code> to remove.
	 */
	final void removeContainedBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		containedBy.remove(role.getId());
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Characteristic) {
			final Characteristic characteristic = (Characteristic) object;
			return getId().equals(characteristic.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}
}
