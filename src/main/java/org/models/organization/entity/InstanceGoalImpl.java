/*
 * InstanceGoalImpl.java
 *
 * Created on Aug 28, 2006
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.entity.basic.SimpleGoalImpl;
import org.models.organization.identifier.UniqueId;

/**
 * The <code>InstanceGoalImpl</code> class implements the {@link InstanceGoal}.
 *
 * @author Christopher Zhong
 * @param <ParameterType>
 *            the type of the parameter for the <code>InstanceGoal</code>.
 * @see SpecificationGoal
 * @since 3.2
 */
public class InstanceGoalImpl<ParameterType> extends SimpleGoalImpl implements InstanceGoal<ParameterType> {

	/**
	 * The <code>InstanceGoalIdentifier</code> class extends the {@link UniqueId} by using two {@link UniqueId} as the form of identification.
	 *
	 * @author Christopher Zhong
	 * @since 4.0
	 * @see UniqueId
	 */
	private static class InstanceGoalIdentifier extends UniqueId {

		/**
		 * Default serial version ID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Internal <code>String</code> for the formatting of <code>InstanceGoalImpl</code> class.
		 */
		private static final String STRING_FORMAT = "%s[%s]";

		/**
		 * The <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 */
		private final UniqueId specificationIdentifier;

		/**
		 * The <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 */
		private final UniqueId instanceIdentifier;

		/**
		 * Optimization for hash code computation since it never changes.
		 */
		private transient Integer hashCode = null;

		/**
		 * Optimization for <code>toString</code> method since it never changes.
		 */
		private transient String toString = null;

		/**
		 * Constructs a new instance of <code>InstanceGoalIdentifier</code>.
		 *
		 * @param specificationIdentifier
		 *            the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 * @param instanceIdentifier
		 *            the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 */
		public InstanceGoalIdentifier(final UniqueId specificationIdentifier, final UniqueId instanceIdentifier) {
			this.specificationIdentifier = specificationIdentifier;
			this.instanceIdentifier = instanceIdentifier;
		}

		/**
		 * Returns the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 *
		 * @return the <code>UniqueIdentifier</code> of the <code>SpecificationGoal</code>.
		 */
		private UniqueId getSpecificationIdentifier() {
			return specificationIdentifier;
		}

		/**
		 * Returns the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 *
		 * @return the <code>UniqueIdentifier</code> of the <code>InstanceGoal</code>.
		 */
		private UniqueId getInstanceIdentifier() {
			return instanceIdentifier;
		}

		@Override
		public boolean equals(final Object object) {
			if (object instanceof InstanceGoalIdentifier) {
				final InstanceGoalIdentifier instanceGoalIdentifier = (InstanceGoalIdentifier) object;
				return getSpecificationIdentifier().equals(instanceGoalIdentifier.getSpecificationIdentifier())
						&& getInstanceIdentifier().equals(instanceGoalIdentifier.getInstanceIdentifier());
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hashCode == null) {
				hashCode = getSpecificationIdentifier().hashCode() << 16 | getInstanceIdentifier().hashCode();
			}
			return hashCode;
		}

		@Override
		public String toString() {
			if (toString == null) {
				toString = String.format(STRING_FORMAT, getSpecificationIdentifier(), getInstanceIdentifier());
			}
			return toString;
		}

	}

	/**
	 * Internal <code>String</code> for the formatting of <code>InstanceGoalImpl</code> class.
	 */
	private static final String STRING_FORMAT = "%s (%s)";

	/**
	 * The <code>SpecificationGoal</code> that created this <code>InstanceGoal</code>.
	 */
	private final SpecificationGoal specificationGoal;

	/**
	 * The <code>UniqueIdentifier</code> identifying the <code>InstanceGoal</code> with respect to other similar <code>InstanceGoal</code> based on the same
	 * <code>SpecificationGoal</code>.
	 */
	private final UniqueId instanceIdentifier;

	/**
	 * The parameter(s) of this <code>InstanceGoal</code>.
	 */
	private final ParameterType parameter;

	/**
	 * Constructs a new instance of <code>InstanceGoal</code> with the given <code>SpecificationGoal</code>, the <code>UniqueIdentifier</code> identifying the
	 * <code>InstanceGoal</code> with respect to the given <code>SpecificationGoal</code>, and the parameter(s).
	 *
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> that this <code>InstanceGoal</code> is based on.
	 * @param instanceIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the instance of this <code>InstanceGoal</code>.
	 * @param parameter
	 *            the parameter(s) of this <code>InstanceGoal</code>.
	 */
	protected InstanceGoalImpl(final SpecificationGoal specificationGoal, final UniqueId instanceIdentifier, final ParameterType parameter) {
		this(specificationGoal, instanceIdentifier, parameter, false);
	}

	/**
	 * Constructs a new instance of <code>InstanceGoal</code> with the given <code>SpecificationGoal</code>, the <code>UniqueIdentifier</code> identifying the
	 * <code>InstanceGoal</code>, and the parameter(s).
	 *
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> that this <code>InstanceGoal</code> is based on.
	 * @param instanceIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the instance of this <code>InstanceGoal</code>.
	 * @param parameter
	 *            the parameter(s) of this <code>InstanceGoal</code>.
	 * @param useSameForGlobal
	 *            <code>true</code> if the given <code>UniqueIdentifier</code> is to be the same as the <code>UniqueIdentifer</code> from
	 *            {@link #getIdentifier()}, <code>false</code> otherwise.
	 */
	public InstanceGoalImpl(final SpecificationGoal specificationGoal, final UniqueId instanceIdentifier, final ParameterType parameter,
			final boolean useSameForGlobal) {
		super(useSameForGlobal ? instanceIdentifier : new InstanceGoalIdentifier(specificationGoal.getIdentifier(), instanceIdentifier));
		this.specificationGoal = specificationGoal;
		this.instanceIdentifier = instanceIdentifier;
		this.parameter = parameter;
	}

	@Override
	public final SpecificationGoal getSpecificationGoal() {
		return specificationGoal;
	}

	@Override
	public final UniqueId getSpecificationIdentifier() {
		return getSpecificationGoal().getIdentifier();
	}

	@Override
	public final UniqueId getInstanceIdentifier() {
		return instanceIdentifier;
	}

	@Override
	public final ParameterType getParameter() {
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
			return super.equals(instanceGoal);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		final ParameterType parameter = getParameter();
		return String.format(STRING_FORMAT, super.toString(), parameter == null ? "" : parameter);
	}

}