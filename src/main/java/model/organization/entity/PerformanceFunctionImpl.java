/*
 * PerformanceFunctionImpl.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.organization.identifier.UniqueIdentifier;
import model.organization.registry.ChangeManager;
import model.organization.registry.EventRegistry;
import model.organization.relation.Assignment;
import model.organization.relation.ModeratesRelation;
import model.organization.relation.UsesRelation;

/**
 * The <code>PerformanceFunctionImpl</code> class implements the
 * {@link PerformanceFunction} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.5 $, $Date: 2011/09/19 14:26:37 $
 * @since 6.0
 */
public class PerformanceFunctionImpl implements PerformanceFunction {

	/**
	 * The <code>UniqueIdentifier</code> representing the
	 * <code>PerformanceFunction</code>.
	 */
	private final UniqueIdentifier identifier;

	/**
	 * The set of <code>Task</code> that are linked to this
	 * <code>PerformanceFunction</code>.
	 */
	private final Map<UniqueIdentifier, UsesRelation> linkedBy = new ConcurrentHashMap<>();

	/**
	 * The <code>Attribute</code> that this <code>PerformanceFunction</code>
	 * moderates.
	 */
	private ModeratesRelation moderates = null;

	/**
	 * Constructs a new instance of <code>PerformanceFunctionImpl</code>.
	 * 
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> representing the
	 *            <code>PerformanceFunction</code>.
	 */
	public PerformanceFunctionImpl(final UniqueIdentifier identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException(
					"Parameter (identifier) cannot be null");
		}
		this.identifier = identifier;
	}

	@Override
	public UniqueIdentifier getIdentifier() {
		return identifier;
	}

	/**
	 * Adds the <code>Task</code> (in the given <code>LinkedRelation</code>) to
	 * the set of <code>Task</code> that is linked to this
	 * <code>PerformanceFunction</code>.
	 * 
	 * @param linkedRelation
	 *            the <code>LinkedRelation</code> containing the
	 *            <code>Task</code> to add.
	 */
	final void addUsedBy(final UsesRelation linkedRelation) {
		if (linkedRelation == null) {
			throw new IllegalArgumentException(
					"Parameter (linkedRelation) cannot be null");
		}
		linkedBy.put(linkedRelation.getRole().getIdentifier(), linkedRelation);
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
	 * Removes the given <code>Role</code> from the set of <code>Role</code>
	 * that is linked to this <code>PerformanceFunction</code>.
	 * 
	 * @param role
	 *            the <code>Role</code> to remove.
	 */
	final void removeUsedBy(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Paramter (role) cannot be null");
		}
		linkedBy.remove(role.getIdentifier());
	}

	@Override
	public final void setModerates(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException(
					"Parameter (attribute) cannot be null");
		}
		if (moderates != null) {
			throw new IllegalArgumentException(
					String.format(
							"Cannot set attribute (%s) as function (%s) already moderates (%s)",
							attribute, getIdentifier(),
							moderates.getAttribute()));
		}
		final ModeratesRelation moderatesRelation = new ModeratesRelation(this,
				attribute);
		moderates = moderatesRelation;
		if (attribute instanceof AttributeImpl) {
			((AttributeImpl) attribute).addModeratedBy(moderatesRelation);
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyModeratesSet(getIdentifier(),
					attribute.getIdentifier());
		}
	}

	@Override
	public final Attribute getModerates() {
		final Attribute result = moderates.getAttribute();
		return result;
	}

	@Override
	public final Double pmf(final Agent<?> agent, final Role role,
			final InstanceGoal<?> goal, final Collection<Assignment> assignments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof PerformanceFunction) {
			final PerformanceFunction performanceFunction = (PerformanceFunction) object;
			return getIdentifier().equals(performanceFunction.getIdentifier());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public String toString() {
		return getIdentifier().toString();
	}

}
