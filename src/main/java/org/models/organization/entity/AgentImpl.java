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

import org.models.organization.entity.basic.SimpleAgent;
import org.models.organization.entity.basic.SimpleAgentImpl;
import org.models.organization.identifier.UniqueId;
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.HasRelation;
import org.models.organization.relation.PossessesRelation;

/**
 * The <code>AgentImpl</code> class implements the {@link Agent} interface.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see SimpleAgent
 * @see Capability
 * @see Role
 * @since 1.0
 */
public class AgentImpl extends SimpleAgentImpl implements Agent {

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
	 * Constructs a new instance of <code>AgentImpl</code>.
	 * <p>
	 *
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>AgentImpl</code>.
	 */
	public AgentImpl(final UniqueId identifier) {
		super(identifier);
	}

	@Override
	public final void addPossesses(final Capability capability, final double score) {
		if (capability == null) {
			throw new IllegalArgumentException("Parameter (capability) cannot be null");
		}
		if (possesses.containsKey(capability.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Agent (%s) already possesses capability (%s)", this, capability));
		}
		final PossessesRelation possessesRelation = new PossessesRelation(this, capability, score);
		possesses.put(capability.getIdentifier(), possessesRelation);
		if (capability instanceof CapabilityImpl) {
			((CapabilityImpl) capability).addPossessedBy(possessesRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPossessesAdded(getIdentifier(), capability.getIdentifier(), score);
		}
	}

	@Override
	public final Capability getPossesses(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final PossessesRelation possessesRelation = possesses.get(capabilityIdentifier);
		return possessesRelation == null ? null : possessesRelation.getCapability();
	}

	@Override
	public final Set<Capability> getPossessesSet() {
		final Set<Capability> result = new HashSet<>();
		for (final PossessesRelation possessesRelation : possesses.values()) {
			result.add(possessesRelation.getCapability());
		}
		return result;
	}

	@Override
	public final double getPossessesScore(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final PossessesRelation possessesRelation = possesses.get(capabilityIdentifier);
		return possessesRelation == null ? PossessesRelation.MIN_SCORE : possessesRelation.getScore();
	}

	@Override
	public final void setPossessesScore(final UniqueId capabilityIdentifier, final double score) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final PossessesRelation possessesRelation = possesses.get(capabilityIdentifier);
		if (possessesRelation == null) {
			throw new IllegalArgumentException(String.format("Cannot set score for a non-existent capability (%s)", capabilityIdentifier));
		}
		possessesRelation.setScore(score);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPossessesChanged(getIdentifier(), possessesRelation.getCapability().getIdentifier(), score);
		}
	}

	@Override
	public final void removePossesses(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		if (possesses.containsKey(capabilityIdentifier)) {
			final PossessesRelation possessesRelation = possesses.remove(capabilityIdentifier);
			final Capability capability = possessesRelation.getCapability();
			if (capability instanceof CapabilityImpl) {
				((CapabilityImpl) capability).removePossessedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyPossessesRemoved(getIdentifier(), capability.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllPossesses() {
		for (final PossessesRelation possessesRelation : possesses.values()) {
			removePossesses(possessesRelation.getCapability().getIdentifier());
		}
	}

	@Override
	public final void addHas(final Attribute attribute, final double value) {
		if (attribute == null) {
			throw new IllegalArgumentException("Paramteter (attribute) cannot be null");
		}
		if (has.containsKey(attribute.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Agent (%s) already has attribute (%s)", this, attribute));
		}
		final HasRelation hasRelation = new HasRelation(this, attribute, value);
		has.put(attribute.getIdentifier(), hasRelation);
		if (attribute instanceof AttributeImpl) {
			((AttributeImpl) attribute).addHadBy(hasRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyHasAdded(getIdentifier(), attribute.getIdentifier(), value);
		}
	}

	@Override
	public final Set<Attribute> getHasSet() {
		final Set<Attribute> result = new HashSet<>();
		for (final HasRelation hasRelation : has.values()) {
			result.add(hasRelation.getAttribute());
		}
		return result;
	}

	@Override
	public final Double getHasValue(final UniqueId attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		final HasRelation hasRelation = has.get(attributeIdentifier);
		return hasRelation == null ? null : hasRelation.getValue();
	}

	@Override
	public final void setHasValue(final UniqueId attributeIdentifier, final double value) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		final HasRelation hasRelation = has.get(attributeIdentifier);
		if (hasRelation == null) {
			throw new IllegalArgumentException(String.format("Cannot set score for a non-existent attribute (%s)", attributeIdentifier));
		}
		hasRelation.setScore(value);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyHasChanged(getIdentifier(), hasRelation.getAttribute().getIdentifier(), value);
		}
	}

	@Override
	public final void removeHas(final UniqueId attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		if (has.containsKey(attributeIdentifier)) {
			final HasRelation hasRelation = has.remove(attributeIdentifier);
			final Attribute attribute = hasRelation.getAttribute();
			if (attribute instanceof AttributeImpl) {
				((AttributeImpl) attribute).removeHadBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyHasRemoved(getIdentifier(), attribute.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllHas() {
		for (final HasRelation hasRelation : has.values()) {
			removeHas(hasRelation.getAttribute().getIdentifier());
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
		final PossessesRelation possessesRelation = possesses.get(capability.getIdentifier());
		return possessesRelation == null ? PossessesRelation.MIN_SCORE : possessesRelation.getScore();
	}

	@Override
	public final Double has(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		final HasRelation hasRelation = has.get(attribute.getIdentifier());
		return hasRelation == null ? null : hasRelation.getValue();
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Agent) {
			final Agent agent = (Agent) object;
			return super.equals(agent);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}