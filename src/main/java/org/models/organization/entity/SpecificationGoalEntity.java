/*
 * SpecificationGoalImpl.java
 *
 * Created on Oct 5, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.factory.InstanceGoalFactory;
import org.models.organization.factory.InstanceGoalFactoryImpl;
import org.models.organization.id.UniqueId;

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
	private final UniqueId<SpecificationGoal> id;

	/**
	 * Constructs a new instance of {@link SpecificationGoal}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@link SpecificationGoal}.
	 */
	public SpecificationGoalEntity(final UniqueId<SpecificationGoal> id) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter (id) cannot be null");
		}
		this.id = id;
	}

	@Override
	public final UniqueId<SpecificationGoal> getId() {
		return id;
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
