/*
 * AttributeImpl.java
 *
 * Created on Oct 15, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.models.organization.identifier.UniqueId;
import org.models.organization.relation.HasRelation;
import org.models.organization.relation.ModeratesRelation;
import org.models.organization.relation.NeedsRelation;

/**
 * The {@link AttributeImpl} class is an implementation of the {@link Attribute}.
 *
 * @author Christopher Zhong
 * @see Attribute
 * @since 5.0
 */
public class AttributeImpl implements Attribute {
	/**
	 * The {@linkplain UniqueId} that represents this {@link Attribute}.
	 */
	private final UniqueId id;

	/**
	 * The {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 */
	private final Type type;

	/**
	 * The set of <code>Role</code> that needs this <code>Attribute</code>.
	 */
	private final Map<UniqueId, NeedsRelation> influencedBy = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Agent</code> that has this <code>Attribute</code>.
	 */
	private final Map<UniqueId, HasRelation> hadBy = new ConcurrentHashMap<>();

	/**
	 * The set of <code>PerformanceFunction</code> that moderates this <code>Attribute</code>.
	 */
	private final Map<UniqueId, ModeratesRelation> moderatedBy = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of {@link Attribute}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@link Attribute}.
	 * @param type
	 *            the {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 */
	public AttributeImpl(final UniqueId id, final Type type) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter (id) cannot be null");
		}
		if (type == null) {
			throw new IllegalArgumentException("Parameter (type) cannot be null");
		}
		this.id = id;
		this.type = type;
	}

	@Override
	public final UniqueId getId() {
		return id;
	}

	@Override
	public final Type getType() {
		return type;
	}

	/**
	 * Adds the given <code>Role</code> to the set of <code>Role</code> that needs this <code>Attribute</code>.
	 *
	 * @param needsRelation
	 *            the <code>Role</code> to add.
	 */
	final void addInfluencedBy(final NeedsRelation needsRelation) {
		if (needsRelation == null) {
			throw new IllegalArgumentException("Parameter (needsRelation) cannot be null");
		}
		influencedBy.put(needsRelation.getRole().getId(), needsRelation);
	}

	@Override
	public final Set<Role> getInfluencedBySet() {
		final Set<Role> result = new HashSet<>();
		for (final NeedsRelation needsRelation : influencedBy.values()) {
			result.add(needsRelation.getRole());
		}
		return result;
	}

	/**
	 * Remove the given <code>Role</code> from the set of <code>Role</code> that needs this <code>Attribute</code>.
	 *
	 * @param role
	 *            the <code>Role</code> to be removed.
	 */
	final void removeInfluencedBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		influencedBy.remove(role.getId());
	}

	/**
	 * Adds the given <code>Agent</code> to the set of <code>Agent</code> that has this <code>Attribute</code>.
	 *
	 * @param hasRelation
	 *            the <code>Agent</code> to be added.
	 */
	final void addHadBy(final HasRelation hasRelation) {
		if (hasRelation == null) {
			throw new IllegalArgumentException("Parameter (hasRelation) cannot be null");
		}
		hadBy.put(hasRelation.getAgent().getId(), hasRelation);
	}

	@Override
	public final Set<Agent> getHadBySet() {
		final Set<Agent> result = new HashSet<>();
		for (final HasRelation hasRelation : hadBy.values()) {
			result.add(hasRelation.getAgent());
		}
		return result;
	}

	@Override
	public final Double getHadByValue(final UniqueId agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifier) cannot be null");
		}
		final HasRelation hasRelation = hadBy.get(agentIdentifier);
		return hasRelation == null ? null : hasRelation.getValue();
	}

	/**
	 * Remove the given <code>Agent</code> from the set of <code>Agent</code> that has this <code>Attribute</code>.
	 *
	 * @param agent
	 *            the <code>Agent</code> to be removed.
	 */
	final void removeHadBy(final Agent agent) {
		if (agent == null) {
			throw new IllegalArgumentException("Parameter (agent) cannot be null");
		}
		hadBy.remove(agent.getId());
	}

	/**
	 * Adds the given <code>PerformanceFunction</code> to the set of <code>PerformanceFunction</code> that moderates this <code>Attribute</code>.
	 *
	 * @param moderatesRelation
	 *            the <code>PerformanceFunction</code> to be added.
	 */
	final void addModeratedBy(final ModeratesRelation moderatesRelation) {
		if (moderatesRelation == null) {
			throw new IllegalArgumentException("Parameter (moderatesRelation) cannot be null");
		}
		moderatedBy.put(moderatesRelation.getPerformanceFunction().getIdentifier(), moderatesRelation);
	}

	@Override
	public final Set<PerformanceFunction> getModeratedBySet() {
		final Set<PerformanceFunction> result = new HashSet<>();
		for (final ModeratesRelation moderatesRelation : moderatedBy.values()) {
			result.add(moderatesRelation.getPerformanceFunction());
		}
		return result;
	}

	/**
	 * Remove the given <code>PerformanceFunction</code> from the set of <code>PerformanceFunction</code> that moderates this <code>Attribute</code>.
	 *
	 * @param performanceFunction
	 *            the <code>PerformanceFunction</code> to remove.
	 */
	final void removeModeratedBy(final PerformanceFunction performanceFunction) {
		if (performanceFunction == null) {
			throw new IllegalArgumentException("Parameter (performanceFunction) cannot be null");
		}
		moderatedBy.remove(performanceFunction.getIdentifier());
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Attribute) {
			final Attribute attribute = (Attribute) object;
			return getId().equals(attribute.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", getId().toString(), getType());
	}

}
