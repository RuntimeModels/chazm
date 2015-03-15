/*
 * InstanceGoalImpl.java
 *
 * Created on Aug 28, 2006
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import org.models.organization.M;
import org.models.organization.id.UniqueId;

/**
 * The {@link InstanceGoal} class is an implementation of the {@link InstanceGoal}.
 *
 * @author Christopher Zhong
 * @see InstanceGoal
 * @since 3.2
 */
public class InstanceGoalEntity implements InstanceGoal {
	/**
	 * The {@linkplain Id} class extends the {@link UniqueId} by using two {@link UniqueId}s; the {@linkplain UniqueId} of a {@linkplain SpecificationGoal} and
	 * the {@linkplain UniqueId} of instance portion of the {@linkplain InstanceGoal}.
	 *
	 * @author Christopher Zhong
	 * @since 4.0
	 * @see UniqueId
	 */
	public static class Id extends UniqueId<InstanceGoal> {
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
		private final UniqueId<SpecificationGoal> specificationId;

		/**
		 * The {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 */
		private final UniqueId<InstanceGoal> instanceId;

		/**
		 * Optimization for hash code computation since it never changes.
		 */
		private transient Integer hashCode = null;

		/**
		 * Optimization for <code>toString</code> method since it never changes.
		 */
		private transient String toString = null;

		/**
		 * Constructs a new instance of {@linkplain Id}.
		 *
		 * @param specificationId
		 *            the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
		 * @param instanceId
		 *            the {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 */
		public Id(final UniqueId<SpecificationGoal> specificationId, final UniqueId<InstanceGoal> instanceId) {
			super(InstanceGoal.class);
			if (specificationId == null) {
				throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "specificationId"));
			}
			if (instanceId == null) {
				throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "instanceId"));
			}
			this.specificationId = specificationId;
			this.instanceId = instanceId;
		}

		/**
		 * Returns the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
		 *
		 * @return the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
		 */
		private UniqueId<SpecificationGoal> getSpecificationId() {
			return specificationId;
		}

		/**
		 * Returns the {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 *
		 * @return the {@linkplain UniqueId} that represents the instance portion of the {@linkplain InstanceGoal}.
		 */
		private UniqueId<InstanceGoal> getInstanceId() {
			return instanceId;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof Id) {
				final Id id = (Id) object;
				return getSpecificationId().equals(id.getSpecificationId()) && getInstanceId().equals(id.getInstanceId());
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
	private final UniqueId<InstanceGoal> id;

	/**
	 * The {@linkplain UniqueId} that represents the instance portion of this {@linkplain InstanceGoal}.
	 */
	private final UniqueId<InstanceGoal> instanceId;

	/**
	 * The {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
	 */
	private final SpecificationGoal specificationGoal;

	/**
	 * The parameter(s) of this <code>InstanceGoal</code>.
	 */
	private final P parameter;

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
	public InstanceGoalEntity(final SpecificationGoal specificationGoal, final UniqueId<InstanceGoal> instanceId, final P parameter) {
		id = new Id(specificationGoal.getId(), instanceId);
		this.instanceId = instanceId;
		this.specificationGoal = specificationGoal;
		this.parameter = parameter;
	}

	@Override
	public UniqueId<InstanceGoal> getId() {
		return id;
	}

	@Override
	public UniqueId<InstanceGoal> getInstanceId() {
		return instanceId;
	}

	@Override
	public SpecificationGoal getSpecificationGoal() {
		return specificationGoal;
	}

	@Override
	public P getParameter() {
		return parameter;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof InstanceGoal) {
			final InstanceGoal instanceGoal = (InstanceGoal) object;
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
		final P parameter = getParameter();
		return String.format(STRING_FORMAT, super.toString(), parameter == null ? "" : parameter);
	}
}