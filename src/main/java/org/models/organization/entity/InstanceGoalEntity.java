/*
 * InstanceGoalImpl.java
 *
 * Created on Aug 28, 2006
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.identifier.UniqueId;

/**
 * The {@link InstanceGoal} class is an implementation of the {@link InstanceGoal}.
 *
 * @author Christopher Zhong
 * @param <T>
 *            the type of the parameter of this {@link InstanceGoal}.
 * @see InstanceGoal
 * @since 3.2
 */
public class InstanceGoalEntity<T> implements InstanceGoal<T> {

	/**
	 * The {@linkplain InstanceGoalEntity.Identifier} class extends the {@link UniqueId} by using two {@link UniqueId}s; the {@linkplain UniqueId} of a
	 * {@linkplain SpecificationGoal} and the {@linkplain UniqueId} of instance portion of the {@linkplain InstanceGoal}.
	 *
	 * @author Christopher Zhong
	 * @since 4.0
	 * @see UniqueId
	 */
	private static class Identifier extends UniqueId {
		/**
		 * Serial version ID
		 */
		private static final long serialVersionUID = 1735177133145542202L;

		/**
		 * Internal <code>String</code> for the formatting of <code>InstanceGoalImpl</code> class.
		 */
		private static final String STRING_FORMAT = "%s[%s]";

		/**
		 * The {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
		 */
		private final UniqueId specificationId;

		/**
		 * The {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 */
		private final UniqueId instanceId;

		/**
		 * Optimization for hash code computation since it never changes.
		 */
		private transient Integer hashCode = null;

		/**
		 * Optimization for <code>toString</code> method since it never changes.
		 */
		private transient String toString = null;

		/**
		 * Constructs a new instance of {@linkplain InstanceGoalEntity.Identifier}.
		 *
		 * @param specificationId
		 *            the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
		 * @param instanceId
		 *            the {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 */
		public Identifier(final UniqueId specificationId, final UniqueId instanceId) {
			if (specificationId == null) {
				throw new IllegalArgumentException("Parameter (specificationId) cannot be null");
			}
			if (instanceId == null) {
				throw new IllegalArgumentException("Parameter (instanceId) cannot be null");
			}
			this.specificationId = specificationId;
			this.instanceId = instanceId;
		}

		/**
		 * Returns the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
		 *
		 * @return the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
		 */
		private UniqueId getSpecificationId() {
			return specificationId;
		}

		/**
		 * Returns the {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 *
		 * @return the {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 */
		private UniqueId getInstanceId() {
			return instanceId;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof Identifier) {
				final Identifier identifier = (Identifier) object;
				return getSpecificationId().equals(identifier.getSpecificationId()) && getInstanceId().equals(identifier.getInstanceId());
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hashCode == null) {
				hashCode = getSpecificationId().hashCode() << 16 | getInstanceId().hashCode();
			}
			return hashCode;
		}

		@Override
		public String toString() {
			if (toString == null) {
				toString = String.format(STRING_FORMAT, getSpecificationId(), getInstanceId());
			}
			return toString;
		}
	}

	/**
	 * Internal <code>String</code> for the formatting of <code>InstanceGoalImpl</code> class.
	 */
	private static final String STRING_FORMAT = "%s (%s)";

	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain InstanceGoal}.
	 */
	private final UniqueId id;

	/**
	 * The {@linkplain UniqueId} that represents the instance portion of this {@linkplain InstanceGoal}.
	 */
	private final UniqueId instanceId;

	/**
	 * The {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
	 */
	private final SpecificationGoal specificationGoal;

	/**
	 * The parameter(s) of this <code>InstanceGoal</code>.
	 */
	private final T parameter;

	/**
	 * Constructs a new instance of {@linkplain InstanceGoal}.
	 *
	 * @param specificationGoal
	 *            the {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
	 * @param instanceId
	 *            the {@linkplain UniqueId} that represents the instance portion of this {@linkplain InstanceGoal}.
	 * @param parameter
	 *            the parameter(s) of this {@linkplain InstanceGoal}.
	 */
	public InstanceGoalEntity(final SpecificationGoal specificationGoal, final UniqueId instanceId, final T parameter) {
		this.id = new Identifier(specificationGoal.getId(), instanceId);
		this.instanceId = instanceId;
		this.specificationGoal = specificationGoal;
		this.parameter = parameter;
	}

	@Override
	public final UniqueId getId() {
		return id;
	}

	@Override
	public final UniqueId getInstanceId() {
		return instanceId;
	}

	@Override
	public final SpecificationGoal getSpecificationGoal() {
		return specificationGoal;
	}

	@Override
	public final T getParameter() {
		return parameter;
	}

	@Override
	public final Set<Role> getAchievedBySet() {
		return getSpecificationGoal().getAchievedBySet();
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof InstanceGoal<?>) {
			final InstanceGoal<?> instanceGoal = (InstanceGoal<?>) object;
			return getId().equals(instanceGoal.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		final T parameter = getParameter();
		return String.format(STRING_FORMAT, super.toString(), parameter == null ? "" : parameter);
	}

}