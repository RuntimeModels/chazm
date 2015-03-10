/*
 * SpecificationGoalImpl.java
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

import org.models.organization.factory.InstanceGoalFactory;
import org.models.organization.factory.InstanceGoalFactoryImpl;
import org.models.organization.identifier.UniqueId;
import org.models.organization.relation.Achieves;
import org.models.organization.relation.AchievesRelation;

/**
 * The {@link SpecificationGoalEntity} class is an implementation of the {@link SpecificationGoal}.
 *
 * @author Scott Harmon, Christopher Zhong
 * @see SpecificationGoal
 * @since 1.0
 */
public class SpecificationGoalEntity implements SpecificationGoal {

	/**
	 * The <code>InstanceGoalFactory</code> that will be used to create new instances of this <code>SpecificationGoal</code> as <code>InstanceGoal</code>.
	 */
	private static InstanceGoalFactory instanceGoalFactory = new InstanceGoalFactoryImpl();

	/**
	 * The {@linkplain UniqueId} that represents this {@link SpecificationGoal}.
	 */
	private final UniqueId id;

	/**
	 * The set of <code>Role</code> that achieves this <code>SpecificationGoal</code>.
	 */
	private final Map<UniqueId, AchievesRelation> achievedBy = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of {@link SpecificationGoal}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@link SpecificationGoal}.
	 */
	public SpecificationGoalEntity(final UniqueId id) {
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
	public final Set<Role> getAchievedBySet() {
		final Set<Role> result = new HashSet<>();
		for (final Achieves achievesRelationImpl : achievedBy.values()) {
			result.add(achievesRelationImpl.getRole());
		}
		return result;
	}

	/**
	 * Removes the given <code>Role</code> from the set of <code>Role</code> that achieves this <code>SpecificationGoal</code>.
	 *
	 * @param role
	 *            the <code>Role</code> to be removed.
	 */
	final void removeAchievedBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		achievedBy.remove(role.getId());
	}

	@Override
	public final <ParameterType> InstanceGoal<ParameterType> getInstanceGoal(final UniqueId instanceIdentifier, final ParameterType parameter) {
		if (instanceIdentifier == null) {
			throw new IllegalArgumentException(String.format("Parameter cannot be null: (Unique Identifier: %s)", instanceIdentifier));
		}
		final InstanceGoal<ParameterType> instanceGoal = instanceGoalFactory.getInstanceGoal(this, instanceIdentifier, parameter);
		return instanceGoal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SpecificationGoal) {
			final SpecificationGoal specificationGoal = (SpecificationGoal) object;
			return getId().equals(specificationGoal.getId());
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

	/**
	 * Sets the <code>InstanceGoalFactory</code> to the given <code>InstanceGoalFactory</code>.
	 *
	 * @param instanceGoalFactory
	 *            the new <>InstanceGoalFactory</code> to be set.
	 */
	public static final void setFactory(final InstanceGoalFactory instanceGoalFactory) {
		if (instanceGoalFactory == null) {
			throw new IllegalArgumentException("Parameter (instanceGoalFactory) cannot be null");
		}
		SpecificationGoalEntity.instanceGoalFactory = instanceGoalFactory;
	}
}
