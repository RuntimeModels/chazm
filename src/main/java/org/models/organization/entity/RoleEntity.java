/*
 * RoleImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.models.organization.function.RoleGoodnessFunction;
import org.models.organization.function.RoleGoodnessFunction.DefaultRoleGoodnessFunction;
import org.models.organization.identifier.UniqueId;
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.Achieves;
import org.models.organization.relation.AchievesRelation;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Contains;
import org.models.organization.relation.ContainsRelation;
import org.models.organization.relation.Needs;
import org.models.organization.relation.NeedsRelation;
import org.models.organization.relation.Requires;
import org.models.organization.relation.RequiresRelation;
import org.models.organization.relation.Uses;
import org.models.organization.relation.UsesRelation;

/**
 * The {@linkplain RoleEntity} class is an implementation of the {@link Role}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Capability
 * @see SpecificationGoal
 * @since 1.0
 */
public class RoleEntity implements Role {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Role}.
	 */
	private final UniqueId id;

	/**
	 * The set of <code>SpecificationGoal</code> that this <code>Role</code> achieves.
	 */
	private final Map<UniqueId, AchievesRelation> achieves = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Capability</code> required by this <code>Role</code>.
	 */
	private final Map<UniqueId, RequiresRelation> requires = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Attribute</code> needed by this <code>Role</code>.
	 */
	private final Map<UniqueId, NeedsRelation> needs = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Characteristic</code> contained by this <code>Role</code>.
	 */
	private final Map<UniqueId, ContainsRelation> contains = new ConcurrentHashMap<>();

	/**
	 * The set of <code>PerformanceFunction</code> that are linked to this <code>Task</code>.
	 */
	private final Map<UniqueId, UsesRelation> uses = new ConcurrentHashMap<>();

	/**
	 * The <code>RoleGoodnessFunction</code> of this <code>Role</code>.
	 */
	private RoleGoodnessFunction goodnessFunction = new DefaultRoleGoodnessFunction();

	/**
	 * Constructs a new instance of {@linkplain Role}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Role}.
	 */
	public RoleEntity(final UniqueId id) {
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
	public final Set<SpecificationGoal> getAchievesSet() {
		final Set<SpecificationGoal> result = new HashSet<>();
		for (final Achieves achievesRelationImpl : achieves.values()) {
			result.add(achievesRelationImpl.getSpecificationGoal());
		}
		return result;
	}

	@Override
	public final void removeAchieves(final UniqueId goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		if (achieves.containsKey(goalIdentifier)) {
			final Achieves achievesRelationImpl = achieves.remove(goalIdentifier);
			final SpecificationGoal specificationGoal = achievesRelationImpl.getSpecificationGoal();
			if (specificationGoal instanceof SpecificationGoalEntity) {
				((SpecificationGoalEntity) specificationGoal).removeAchievedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyAchievesRemoved(getId(), specificationGoal.getId());
			}
		}
	}

	@Override
	public final void removeAllAchieves() {
		for (final Achieves achievesRelationImpl : achieves.values()) {
			removeAchieves(achievesRelationImpl.getSpecificationGoal().getId());
		}
	}

	@Override
	public final void addRequires(final Capability capability) {
		if (capability == null) {
			throw new IllegalArgumentException("Parameter (capability) cannot be null");
		}
		if (requires.containsKey(capability.getId())) {
			throw new IllegalArgumentException(String.format("Role (%s) already requires capability (%s)", this, capability));
		}
		final RequiresRelation requiresRelation = new RequiresRelation(this, capability);
		requires.put(capability.getId(), requiresRelation);
		if (capability instanceof CapabilityEntity) {
			((CapabilityEntity) capability).addRequiredBy(requiresRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRequiresAdded(getId(), capability.getId());
		}
	}

	@Override
	public final void addRequiresSet(final Collection<Capability> capabilities) {
		if (capabilities == null) {
			throw new IllegalArgumentException("Parameter (capabilities) cannot be null");
		}
		for (final Capability capability : capabilities) {
			addRequires(capability);
		}
	}

	@Override
	public final Set<Capability> getRequiresSet() {
		final Set<Capability> result = new HashSet<>();
		for (final Requires requiresRelation : requires.values()) {
			result.add(requiresRelation.getCapability());
		}
		return result;
	}

	@Override
	public final void removeRequires(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		if (requires.containsKey(capabilityIdentifier)) {
			final Requires requiresRelation = requires.remove(capabilityIdentifier);
			final Capability capability = requiresRelation.getCapability();
			if (capability instanceof CapabilityEntity) {
				((CapabilityEntity) capability).removeRequiredBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyRequiresRemoves(getId(), capability.getId());
			}
		}
	}

	@Override
	public final void removeAllRequires() {
		for (final Requires requiresRelation : requires.values()) {
			removeRequires(requiresRelation.getCapability().getId());
		}
	}

	@Override
	public final void addNeeds(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		if (needs.containsKey(attribute.getId())) {
			throw new IllegalArgumentException(String.format("Role (%s) already needs attribute (%s)", this, attribute));
		}
		final NeedsRelation needsRelation = new NeedsRelation(this, attribute);
		needs.put(attribute.getId(), needsRelation);
		if (attribute instanceof AttributeEntity) {
			((AttributeEntity) attribute).addInfluencedBy(needsRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInfluencesAdded(getId(), attribute.getId());
		}
	}

	@Override
	public final Set<Attribute> getNeedsSet() {
		final Set<Attribute> result = new HashSet<>();
		for (final Needs needsRelation : needs.values()) {
			result.add(needsRelation.getAttribute());
		}
		return result;
	}

	@Override
	public final void removeNeeds(final UniqueId attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		if (needs.containsKey(attributeIdentifier)) {
			final Needs needsRelation = needs.remove(attributeIdentifier);
			final Attribute attribute = needsRelation.getAttribute();
			if (attribute instanceof AttributeEntity) {
				((AttributeEntity) attribute).removeInfluencedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyInfluencesRemoved(getId(), attribute.getId());
			}
		}
	}

	@Override
	public final void removeAllNeeds() {
		for (final Needs needsRelation : needs.values()) {
			removeNeeds(needsRelation.getAttribute().getId());
		}
	}

	@Override
	public final void addContains(final Characteristic characteristic, final double value) {
		if (characteristic == null) {
			throw new IllegalArgumentException("Parameter (characteristic) cannot be null");
		}
		if (contains.containsKey(characteristic.getId())) {
			throw new IllegalArgumentException(String.format("Role (%s) already contains characteristic (%s)", this, characteristic));
		}
		final ContainsRelation containsRelation = new ContainsRelation(this, characteristic, value);
		contains.put(characteristic.getId(), containsRelation);
		if (characteristic instanceof CharacteristicEntity) {
			((CharacteristicEntity) characteristic).addContainedBy(containsRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyContainsAdded(getId(), characteristic.getId(), value);
		}
	}

	@Override
	public final Set<Characteristic> getContainsSet() {
		final Set<Characteristic> result = new HashSet<>();
		for (final Contains containsRelation : contains.values()) {
			result.add(containsRelation.getCharacteristic());
		}
		return result;
	}

	@Override
	public final Double getContainsValue(final UniqueId characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicIdentifier) cannot be null");
		}
		final Contains containsRelation = contains.get(characteristicIdentifier);
		return containsRelation == null ? null : containsRelation.getValue();
	}

	@Override
	public final void setContainsValue(final UniqueId characteristicIdentifier, final double value) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicIdentifier) cannot be null");
		}
		final Contains containsRelation = contains.get(characteristicIdentifier);
		if (containsRelation == null) {
			throw new IllegalArgumentException(String.format("Cannot set score for a non-existent characteristic (%s=%s)", characteristicIdentifier,
					containsRelation));
		}
		containsRelation.setValue(value);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyContainsChanged(getId(), containsRelation.getCharacteristic().getId(), value);
		}
	}

	@Override
	public final void removeContains(final UniqueId characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicsIdentifier) cannot be null");
		}
		if (contains.containsKey(characteristicIdentifier)) {
			final Contains containsRelation = contains.remove(characteristicIdentifier);
			final Characteristic characterisitic = containsRelation.getCharacteristic();
			if (characterisitic instanceof CharacteristicEntity) {
				((CharacteristicEntity) characterisitic).removeContainedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyContainsRemoved(getId(), characterisitic.getId());
			}
		}
	}

	@Override
	public final void removeAllContains() {
		for (final Contains containsRelation : contains.values()) {
			removeContains(containsRelation.getCharacteristic().getId());
		}
	}

	@Override
	public final void addUses(final PerformanceFunction performanceFunction) {
		if (performanceFunction == null) {
			throw new IllegalArgumentException("Parameter (performanceFunction) cannot be null");
		}
		if (uses.containsKey(performanceFunction.getId())) {
			throw new IllegalArgumentException(String.format("Task (%s) already linked to performance function (%s)", this, performanceFunction));
		}
		final UsesRelation linkedRelation = new UsesRelation(this, performanceFunction);
		uses.put(performanceFunction.getId(), linkedRelation);
		if (performanceFunction instanceof PerformanceFunctionEntity) {
			((PerformanceFunctionEntity) performanceFunction).addUsedBy(linkedRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyLinkedAdded(getId(), performanceFunction.getId());
		}
	}

	@Override
	public final PerformanceFunction getUses(final UniqueId functionIdentifer) {
		if (functionIdentifer == null) {
			throw new IllegalArgumentException("Parameter (functionIdentifier) cannot be null");
		}
		final Uses usesRelation = uses.get(functionIdentifer);
		return usesRelation == null ? null : usesRelation.getPerformanceFunction();
	}

	@Override
	public final Set<PerformanceFunction> getUsesSet() {
		final Set<PerformanceFunction> result = new HashSet<>();
		for (final Uses linkedRelation : uses.values()) {
			result.add(linkedRelation.getPerformanceFunction());
		}
		return result;
	}

	@Override
	public final void removeUses(final UniqueId functionIdentifier) {
		if (functionIdentifier == null) {
			throw new IllegalArgumentException("Parameter (functionIdentifier) cannot be null");
		}
		if (uses.containsKey(functionIdentifier)) {
			final Uses linkedRelation = uses.remove(functionIdentifier);
			final PerformanceFunction performanceFunction = linkedRelation.getPerformanceFunction();
			if (performanceFunction instanceof PerformanceFunctionEntity) {
				((PerformanceFunctionEntity) performanceFunction).removeUsedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyLinkedRemoved(getId(), performanceFunction.getId());
			}
		}
	}

	@Override
	public final void setGoodnessFunction(final RoleGoodnessFunction goodnessFunction) {
		this.goodnessFunction = goodnessFunction;
	}

	@Override
	public final boolean achieves(final SpecificationGoal specificationGoal) {
		final Achieves achievesRelationImpl = achieves.get(specificationGoal.getId());
		return achievesRelationImpl != null;
	}

	@Override
	public final boolean achieves(final InstanceGoal<?> goal) {
		return achieves(goal.getSpecificationGoal());
	}

	@Override
	public final boolean requires(final Capability capability) {
		return requires.containsKey(capability.getId());
	}

	@Override
	public final boolean influences(final Attribute attribute) {
		return needs.containsKey(attribute.getId());
	}

	@Override
	public final Double contains(final Characteristic characteristic) {
		final Contains containsRelation = contains.get(characteristic.getId());
		return containsRelation == null ? null : containsRelation.getValue();
	}

	@Override
	public final double goodness(final Agent agent, final InstanceGoal<?> goal, final Collection<Assignment> assignments) {
		return goodnessFunction.goodness(this, agent, goal, assignments);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Role) {
			final Role role = (Role) object;
			return getId().equals(role.getId());
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
