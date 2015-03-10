/*
 * PerformanceFunctionImpl.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.models.organization.identifier.UniqueId;
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Moderates;
import org.models.organization.relation.ModeratesRelation;
import org.models.organization.relation.UsesRelation;

/**
 * The {@linkplain PerformanceFunctionEntity} class is an implementation of the {@link PerformanceFunction}.
 *
 * @author Christopher Zhong
 * @see PerformanceFunction
 * @since 6.0
 */
public class PerformanceFunctionEntity implements PerformanceFunction {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain PerformanceFunction}.
	 */
	private final UniqueId id;

	/**
	 * The set of <code>Task</code> that are linked to this <code>PerformanceFunction</code>.
	 */
	private final Map<UniqueId, UsesRelation> linkedBy = new ConcurrentHashMap<>();

	/**
	 * The <code>Attribute</code> that this <code>PerformanceFunction</code> moderates.
	 */
	private Moderates moderates = null;

	/**
	 * Constructs a new instance of {@linkplain PerformanceFunction}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain PerformanceFunction}.
	 */
	public PerformanceFunctionEntity(final UniqueId id) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter (id) cannot be null");
		}
		this.id = id;
	}

	@Override
	public UniqueId getId() {
		return id;
	}

	/**
	 * Adds the <code>Task</code> (in the given <code>LinkedRelation</code>) to the set of <code>Task</code> that is linked to this
	 * <code>PerformanceFunction</code>.
	 *
	 * @param linkedRelation
	 *            the <code>LinkedRelation</code> containing the <code>Task</code> to add.
	 */
	final void addUsedBy(final UsesRelation linkedRelation) {
		if (linkedRelation == null) {
			throw new IllegalArgumentException("Parameter (linkedRelation) cannot be null");
		}
		linkedBy.put(linkedRelation.getRole().getId(), linkedRelation);
	}

	@Override
	public final Set<Role> getUsedBySet() {
		final Set<Role> result = new HashSet<>();
		for (final UsesRelation linkedRelation : linkedBy.values()) {
			result.add(linkedRelation.getRole());
		}
		return result;
	}

	/**
	 * Removes the given <code>Role</code> from the set of <code>Role</code> that is linked to this <code>PerformanceFunction</code>.
	 *
	 * @param role
	 *            the <code>Role</code> to remove.
	 */
	final void removeUsedBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Paramter (role) cannot be null");
		}
		linkedBy.remove(role.getId());
	}

	@Override
	public final void setModerates(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		if (moderates != null) {
			throw new IllegalArgumentException(String.format("Cannot set attribute (%s) as function (%s) already moderates (%s)", attribute, getId(),
					moderates.getAttribute()));
		}
		final ModeratesRelation moderatesRelation = new ModeratesRelation(this, attribute);
		moderates = moderatesRelation;
		if (attribute instanceof AttributeEntity) {
			((AttributeEntity) attribute).addModeratedBy(moderatesRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyModeratesSet(getId(), attribute.getId());
		}
	}

	@Override
	public final Attribute getModerates() {
		final Attribute result = moderates.getAttribute();
		return result;
	}

	@Override
	public final Double pmf(final Agent agent, final Role role, final InstanceGoal<?> goal, final Collection<Assignment> assignments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof PerformanceFunction) {
			final PerformanceFunction performanceFunction = (PerformanceFunction) object;
			return getId().equals(performanceFunction.getId());
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
