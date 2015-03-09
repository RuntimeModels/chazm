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

import org.models.organization.entity.basic.SimpleRoleImpl;
import org.models.organization.function.RoleGoodnessFunction;
import org.models.organization.function.RoleGoodnessFunction.DefaultRoleGoodnessFunction;
import org.models.organization.identifier.UniqueIdentifier;
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.AchievesRelation;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.ContainsRelation;
import org.models.organization.relation.NeedsRelation;
import org.models.organization.relation.RequiresRelation;
import org.models.organization.relation.UsesRelation;

/**
 * The <code>RoleImpl</code> class implements the {@link Role} interface.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see Agent
 * @see Capability
 * @see SpecificationGoal
 * @since 1.0
 */
public class RoleImpl extends SimpleRoleImpl implements Role {

	/**
	 * The set of <code>SpecificationGoal</code> that this <code>Role</code> achieves.
	 */
	private final Map<UniqueIdentifier, AchievesRelation> achieves = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Capability</code> required by this <code>Role</code>.
	 */
	private final Map<UniqueIdentifier, RequiresRelation> requires = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Attribute</code> needed by this <code>Role</code>.
	 */
	private final Map<UniqueIdentifier, NeedsRelation> needs = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Characteristic</code> contained by this <code>Role</code>.
	 */
	private final Map<UniqueIdentifier, ContainsRelation> contains = new ConcurrentHashMap<>();

	/**
	 * The set of <code>PerformanceFunction</code> that are linked to this <code>Task</code>.
	 */
	private final Map<UniqueIdentifier, UsesRelation> uses = new ConcurrentHashMap<>();

	/**
	 * The <code>RoleGoodnessFunction</code> of this <code>Role</code>.
	 */
	private RoleGoodnessFunction goodnessFunction = new DefaultRoleGoodnessFunction();

	/**
	 * Constructs a new instance of <code>RoleImpl</code>.
	 *
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> identifying this <code>Role</code>.
	 */
	public RoleImpl(final UniqueIdentifier identifier) {
		super(identifier);
	}

	@Override
	public final Set<SpecificationGoal> getAchievesSet() {
		final Set<SpecificationGoal> result = new HashSet<>();
		for (final AchievesRelation achievesRelation : achieves.values()) {
			result.add(achievesRelation.getSpecificationGoal());
		}
		return result;
	}

	@Override
	public final void removeAchieves(final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		if (achieves.containsKey(goalIdentifier)) {
			final AchievesRelation achievesRelation = achieves.remove(goalIdentifier);
			final SpecificationGoal specificationGoal = achievesRelation.getSpecificationGoal();
			if (specificationGoal instanceof SpecificationGoalImpl) {
				((SpecificationGoalImpl) specificationGoal).removeAchievedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyAchievesRemoved(getIdentifier(), specificationGoal.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllAchieves() {
		for (final AchievesRelation achievesRelation : achieves.values()) {
			removeAchieves(achievesRelation.getSpecificationGoal().getIdentifier());
		}
	}

	@Override
	public final void addRequires(final Capability capability) {
		if (capability == null) {
			throw new IllegalArgumentException("Parameter (capability) cannot be null");
		}
		if (requires.containsKey(capability.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Role (%s) already requires capability (%s)", this, capability));
		}
		final RequiresRelation requiresRelation = new RequiresRelation(this, capability);
		requires.put(capability.getIdentifier(), requiresRelation);
		if (capability instanceof CapabilityImpl) {
			((CapabilityImpl) capability).addRequiredBy(requiresRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRequiresAdded(getIdentifier(), capability.getIdentifier());
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
		for (final RequiresRelation requiresRelation : requires.values()) {
			result.add(requiresRelation.getCapability());
		}
		return result;
	}

	@Override
	public final void removeRequires(final UniqueIdentifier capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		if (requires.containsKey(capabilityIdentifier)) {
			final RequiresRelation requiresRelation = requires.remove(capabilityIdentifier);
			final Capability capability = requiresRelation.getCapability();
			if (capability instanceof CapabilityImpl) {
				((CapabilityImpl) capability).removeRequiredBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyRequiresRemoves(getIdentifier(), capability.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllRequires() {
		for (final RequiresRelation requiresRelation : requires.values()) {
			removeRequires(requiresRelation.getCapability().getIdentifier());
		}
	}

	@Override
	public final void addNeeds(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		if (needs.containsKey(attribute.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Role (%s) already needs attribute (%s)", this, attribute));
		}
		final NeedsRelation needsRelation = new NeedsRelation(this, attribute);
		needs.put(attribute.getIdentifier(), needsRelation);
		if (attribute instanceof AttributeImpl) {
			((AttributeImpl) attribute).addInfluencedBy(needsRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInfluencesAdded(getIdentifier(), attribute.getIdentifier());
		}
	}

	@Override
	public final Set<Attribute> getNeedsSet() {
		final Set<Attribute> result = new HashSet<>();
		for (final NeedsRelation needsRelation : needs.values()) {
			result.add(needsRelation.getAttribute());
		}
		return result;
	}

	@Override
	public final void removeNeeds(final UniqueIdentifier attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		if (needs.containsKey(attributeIdentifier)) {
			final NeedsRelation needsRelation = needs.remove(attributeIdentifier);
			final Attribute attribute = needsRelation.getAttribute();
			if (attribute instanceof AttributeImpl) {
				((AttributeImpl) attribute).removeInfluencedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyInfluencesRemoved(getIdentifier(), attribute.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllNeeds() {
		for (final NeedsRelation needsRelation : needs.values()) {
			removeNeeds(needsRelation.getAttribute().getIdentifier());
		}
	}

	@Override
	public final void addContains(final Characteristic characteristic, final double value) {
		if (characteristic == null) {
			throw new IllegalArgumentException("Parameter (characteristic) cannot be null");
		}
		if (contains.containsKey(characteristic.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Role (%s) already contains characteristic (%s)", this, characteristic));
		}
		final ContainsRelation containsRelation = new ContainsRelation(this, characteristic, value);
		contains.put(characteristic.getIdentifier(), containsRelation);
		if (characteristic instanceof CharacteristicImpl) {
			((CharacteristicImpl) characteristic).addContainedBy(containsRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyContainsAdded(getIdentifier(), characteristic.getIdentifier(), value);
		}
	}

	@Override
	public final Set<Characteristic> getContainsSet() {
		final Set<Characteristic> result = new HashSet<>();
		for (final ContainsRelation containsRelation : contains.values()) {
			result.add(containsRelation.getCharacteristic());
		}
		return result;
	}

	@Override
	public final Double getContainsValue(final UniqueIdentifier characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicIdentifier) cannot be null");
		}
		final ContainsRelation containsRelation = contains.get(characteristicIdentifier);
		return containsRelation == null ? null : containsRelation.getValue();
	}

	@Override
	public final void setContainsValue(final UniqueIdentifier characteristicIdentifier, final double value) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicIdentifier) cannot be null");
		}
		final ContainsRelation containsRelation = contains.get(characteristicIdentifier);
		if (containsRelation == null) {
			throw new IllegalArgumentException(String.format("Cannot set score for a non-existent characteristic (%s=%s)", characteristicIdentifier,
					containsRelation));
		}
		containsRelation.setValue(value);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyContainsChanged(getIdentifier(), containsRelation.getCharacteristic().getIdentifier(), value);
		}
	}

	@Override
	public final void removeContains(final UniqueIdentifier characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicsIdentifier) cannot be null");
		}
		if (contains.containsKey(characteristicIdentifier)) {
			final ContainsRelation containsRelation = contains.remove(characteristicIdentifier);
			final Characteristic characterisitic = containsRelation.getCharacteristic();
			if (characterisitic instanceof CharacteristicImpl) {
				((CharacteristicImpl) characterisitic).removeContainedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyContainsRemoved(getIdentifier(), characterisitic.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllContains() {
		for (final ContainsRelation containsRelation : contains.values()) {
			removeContains(containsRelation.getCharacteristic().getIdentifier());
		}
	}

	@Override
	public final void addUses(final PerformanceFunction performanceFunction) {
		if (performanceFunction == null) {
			throw new IllegalArgumentException("Parameter (performanceFunction) cannot be null");
		}
		if (uses.containsKey(performanceFunction.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Task (%s) already linked to performance function (%s)", this, performanceFunction));
		}
		final UsesRelation linkedRelation = new UsesRelation(this, performanceFunction);
		uses.put(performanceFunction.getIdentifier(), linkedRelation);
		if (performanceFunction instanceof PerformanceFunctionImpl) {
			((PerformanceFunctionImpl) performanceFunction).addUsedBy(linkedRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyLinkedAdded(getIdentifier(), performanceFunction.getIdentifier());
		}
	}

	@Override
	public final PerformanceFunction getUses(final UniqueIdentifier functionIdentifer) {
		if (functionIdentifer == null) {
			throw new IllegalArgumentException("Parameter (functionIdentifier) cannot be null");
		}
		final UsesRelation usesRelation = uses.get(functionIdentifer);
		return usesRelation == null ? null : usesRelation.getPerformanceFunction();
	}

	@Override
	public final Set<PerformanceFunction> getUsesSet() {
		final Set<PerformanceFunction> result = new HashSet<>();
		for (final UsesRelation linkedRelation : uses.values()) {
			result.add(linkedRelation.getPerformanceFunction());
		}
		return result;
	}

	@Override
	public final void removeUses(final UniqueIdentifier functionIdentifier) {
		if (functionIdentifier == null) {
			throw new IllegalArgumentException("Parameter (functionIdentifier) cannot be null");
		}
		if (uses.containsKey(functionIdentifier)) {
			final UsesRelation linkedRelation = uses.remove(functionIdentifier);
			final PerformanceFunction performanceFunction = linkedRelation.getPerformanceFunction();
			if (performanceFunction instanceof PerformanceFunctionImpl) {
				((PerformanceFunctionImpl) performanceFunction).removeUsedBy(this);
			}

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyLinkedRemoved(getIdentifier(), performanceFunction.getIdentifier());
			}
		}
	}

	@Override
	public final void setGoodnessFunction(final RoleGoodnessFunction goodnessFunction) {
		this.goodnessFunction = goodnessFunction;
	}

	@Override
	public final boolean achieves(final SpecificationGoal specificationGoal) {
		final AchievesRelation achievesRelation = achieves.get(specificationGoal.getIdentifier());
		return achievesRelation != null;
	}

	@Override
	public final boolean achieves(final InstanceGoal<?> goal) {
		return achieves(goal.getSpecificationGoal());
	}

	@Override
	public final boolean requires(final Capability capability) {
		return requires.containsKey(capability.getIdentifier());
	}

	@Override
	public final boolean influences(final Attribute attribute) {
		return needs.containsKey(attribute.getIdentifier());
	}

	@Override
	public final Double contains(final Characteristic characteristic) {
		final ContainsRelation containsRelation = contains.get(characteristic.getIdentifier());
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
			return super.equals(role);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
