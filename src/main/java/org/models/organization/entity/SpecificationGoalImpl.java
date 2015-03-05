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

import org.models.organization.entity.basic.SimpleGoalImpl;
import org.models.organization.factory.InstanceGoalFactory;
import org.models.organization.factory.InstanceGoalFactoryImpl;
import org.models.organization.identifier.UniqueIdentifier;
import org.models.organization.relation.AchievesRelation;

/**
 * The <code>SpecificationGoalImpl</code> class implements the
 * {@link SpecificationGoal} interface.
 * 
 * @author Scott Harmon, Christopher Zhong
 * @version $Revision: 1.5.2.6 $, $Date: 2011/09/19 14:26:37 $
 * @see Role
 * @since 1.0
 */
public class SpecificationGoalImpl extends SimpleGoalImpl implements
		SpecificationGoal {

	/**
	 * The <code>InstanceGoalFactory</code> that will be used to create new
	 * instances of this <code>SpecificationGoal</code> as
	 * <code>InstanceGoal</code>.
	 */
	private static InstanceGoalFactory instanceGoalFactory = new InstanceGoalFactoryImpl();

	/**
	 * The set of <code>Role</code> that achieves this
	 * <code>SpecificationGoal</code>.
	 */
	private final Map<UniqueIdentifier, AchievesRelation> achievedBy = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of <code>SpecificationGoal</code> with the
	 * given <code>UniqueIdentifier</code>.
	 * 
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> identifying the
	 *            <code>SpecificationGoal</code>.
	 */
	public SpecificationGoalImpl(final UniqueIdentifier identifier) {
		super(identifier);
	}

	/**
	 * Adds the given <code>AchievesRelation</code> to the set of
	 * <code>AchievesRelation</code> that achieves this
	 * <code>SpecificationGoal</code>.
	 * <p>
	 * The <code>AchievesRelation</code> will be indexed by the
	 * <code>String</code> identifying the <code>Role</code> associated with the
	 * given <code>AchievesRelation</code>.
	 * 
	 * @param achievesRelation
	 *            the <code>AchievesRelation</code> to be added.
	 */
	final void addAchievedBy(final AchievesRelation achievesRelation) {
		if (achievesRelation == null) {
			throw new IllegalArgumentException(
					"Parameter (achievesRelation) cannot be null");
		}
		achievedBy.put(achievesRelation.getRole().getIdentifier(),
				achievesRelation);
	}

	@Override
	public final Set<Role> getAchievedBySet() {
		final Set<Role> result = new HashSet<>();
		for (final AchievesRelation achievesRelation : achievedBy.values()) {
			result.add(achievesRelation.getRole());
		}
		return result;
	}

	/**
	 * Removes the given <code>Role</code> from the set of <code>Role</code>
	 * that achieves this <code>SpecificationGoal</code>.
	 * 
	 * @param role
	 *            the <code>Role</code> to be removed.
	 */
	final void removeAchievedBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException(
					"Parameter (role) cannot be null");
		}
		achievedBy.remove(role.getIdentifier());
	}

	@Override
	public final <ParameterType> InstanceGoal<ParameterType> getInstanceGoal(
			final UniqueIdentifier instanceIdentifier,
			final ParameterType parameter) {
		if (instanceIdentifier == null) {
			throw new IllegalArgumentException(String.format(
					"Parameter cannot be null: (Unique Identifier: %s)",
					instanceIdentifier));
		}
		final InstanceGoal<ParameterType> instanceGoal = instanceGoalFactory
				.getInstanceGoal(this, instanceIdentifier, parameter);
		return instanceGoal;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SpecificationGoal) {
			final SpecificationGoal specificationGoal = (SpecificationGoal) object;
			return super.equals(specificationGoal);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Sets the <code>InstanceGoalFactory</code> to the given
	 * <code>InstanceGoalFactory</code>.
	 * 
	 * @param instanceGoalFactory
	 *            the new <>InstanceGoalFactory</code> to be set.
	 */
	public static final void setFactory(
			final InstanceGoalFactory instanceGoalFactory) {
		if (instanceGoalFactory == null) {
			throw new IllegalArgumentException(
					"Parameter (instanceGoalFactory) cannot be null");
		}
		SpecificationGoalImpl.instanceGoalFactory = instanceGoalFactory;
	}
}
