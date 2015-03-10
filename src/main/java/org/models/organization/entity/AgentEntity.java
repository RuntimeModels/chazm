/*
 * AgentImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.models.organization.identifier.UniqueId;
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.Has;
import org.models.organization.relation.HasRelation;
import org.models.organization.relation.Possesses;
import org.models.organization.relation.PossessesRelation;

/**
 * The {@link AgentEntity} class is an implementation of the {@link Agent}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Capability
 * @see Role
 * @since 1.0
 */
public class AgentEntity implements Agent {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Agent}.
	 */
	private final UniqueId id;

	/**
	 * The set of <code>Capability</code> that this <code>Agent</code> possesses.
	 */
	private final Map<UniqueId, PossessesRelation> possesses = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Attribute</code> that this <code>Agent</code> has.
	 */
	private final Map<UniqueId, HasRelation> has = new ConcurrentHashMap<>();

	/**
	 * Contains contact information on how to contact this agent.
	 */
	private ContactInfo contactInfo = null;

	/**
	 * Constructs a new instance of {@linkplain Agent}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Agent}.
	 */
	public AgentEntity(final UniqueId id) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter (id) cannot be null");
		}
		this.id = id;
	}

	@Override
	public final UniqueId getId() {
		return id;
	}

	@Override
	public final void addPossesses(final Capability capability, final double score) {
		if (capability == null) {
			throw new IllegalArgumentException("Parameter (capability) cannot be null");
		}
		if (possesses.containsKey(capability.getId())) {
			throw new IllegalArgumentException(String.format("Agent (%s) already possesses capability (%s)", this, capability));
		}
		final PossessesRelation possessesRelation = new PossessesRelation(this, capability, score);
		possesses.put(capability.getId(), possessesRelation);
		if (capability instanceof CapabilityEntity) {
			((CapabilityEntity) capability).addPossessedBy(possessesRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPossessesAdded(getId(), capability.getId(), score);
		}
	}

	@Override
	public final Capability getPossesses(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final Possesses possessesRelation = possesses.get(capabilityIdentifier);
		return possessesRelation == null ? null : possessesRelation.getCapability();
	}

	@Override
	public final Set<Capability> getPossessesSet() {
		final Set<Capability> result = new HashSet<>();
		for (final Possesses possessesRelation : possesses.values()) {
			result.add(possessesRelation.getCapability());
		}
		return result;
	}

	@Override
	public final double getPossessesScore(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final Possesses possessesRelation = possesses.get(capabilityIdentifier);
		return possessesRelation == null ? Possesses.MIN_SCORE : possessesRelation.getScore();
	}

	@Override
	public final void setPossessesScore(final UniqueId capabilityIdentifier, final double score) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final Possesses possessesRelation = possesses.get(capabilityIdentifier);
		if (possessesRelation == null) {
			throw new IllegalArgumentException(String.format("Cannot set score for a non-existent capability (%s)", capabilityIdentifier));
		}
		possessesRelation.setScore(score);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPossessesChanged(getId(), possessesRelation.getCapability().getId(), score);
		}
	}

	@Override
	public final void removePossesses(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		if (possesses.containsKey(capabilityIdentifier)) {
			final Possesses possessesRelation = possesses.remove(capabilityIdentifier);
			final Capability capability = possessesRelation.getCapability();
			if (capability instanceof CapabilityEntity) {
				((CapabilityEntity) capability).removePossessedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyPossessesRemoved(getId(), capability.getId());
			}
		}
	}

	@Override
	public final void removeAllPossesses() {
		for (final Possesses possessesRelation : possesses.values()) {
			removePossesses(possessesRelation.getCapability().getId());
		}
	}

	@Override
	public final void addHas(final Attribute attribute, final double value) {
		if (attribute == null) {
			throw new IllegalArgumentException("Paramteter (attribute) cannot be null");
		}
		if (has.containsKey(attribute.getId())) {
			throw new IllegalArgumentException(String.format("Agent (%s) already has attribute (%s)", this, attribute));
		}
		final HasRelation hasRelation = new HasRelation(this, attribute, value);
		has.put(attribute.getId(), hasRelation);
		if (attribute instanceof AttributeEntity) {
			((AttributeEntity) attribute).addHadBy(hasRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyHasAdded(getId(), attribute.getId(), value);
		}
	}

	@Override
	public final Set<Attribute> getHasSet() {
		final Set<Attribute> result = new HashSet<>();
		for (final Has hasRelation : has.values()) {
			result.add(hasRelation.getAttribute());
		}
		return result;
	}

	@Override
	public final Double getHasValue(final UniqueId attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		final Has hasRelation = has.get(attributeIdentifier);
		return hasRelation == null ? null : hasRelation.getValue();
	}

	@Override
	public final void setHasValue(final UniqueId attributeIdentifier, final double value) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		final Has hasRelation = has.get(attributeIdentifier);
		if (hasRelation == null) {
			throw new IllegalArgumentException(String.format("Cannot set score for a non-existent attribute (%s)", attributeIdentifier));
		}
		hasRelation.setValue(value);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyHasChanged(getId(), hasRelation.getAttribute().getId(), value);
		}
	}

	@Override
	public final void removeHas(final UniqueId attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		if (has.containsKey(attributeIdentifier)) {
			final Has hasRelation = has.remove(attributeIdentifier);
			final Attribute attribute = hasRelation.getAttribute();
			if (attribute instanceof AttributeEntity) {
				((AttributeEntity) attribute).removeHadBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyHasRemoved(getId(), attribute.getId());
			}
		}
	}

	@Override
	public final void removeAllHas() {
		for (final Has hasRelation : has.values()) {
			removeHas(hasRelation.getAttribute().getId());
		}
	}

	@Override
	public final ContactInfo getContactInfo() {
		return contactInfo;
	}

	@Override
	public final void setContactInfo(final ContactInfo contactInfo) {
		if (contactInfo == null) {
			throw new IllegalArgumentException(String.format("Contact information (%s) cannot be null", contactInfo));
		}
		this.contactInfo = contactInfo;
	}

	@Override
	public final double possesses(final Capability capability) {
		if (capability == null) {
			throw new IllegalArgumentException("Parameter (capability) cannot be null");
		}
		final Possesses possessesRelation = possesses.get(capability.getId());
		return possessesRelation == null ? Possesses.MIN_SCORE : possessesRelation.getScore();
	}

	@Override
	public final Double has(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		final Has hasRelation = has.get(attribute.getId());
		return hasRelation == null ? null : hasRelation.getValue();
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Agent) {
			final Agent agent = (Agent) object;
			return getId().equals(agent.getId());
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