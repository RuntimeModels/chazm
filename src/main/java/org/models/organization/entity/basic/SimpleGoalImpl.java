/*
 * SimpleGoalImpl.java
 *
 * Created on Mar 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity.basic;

import java.util.HashMap;
import java.util.Map;

import org.models.organization.identifier.UniqueId;

/**
 * The <code>SimpleGoalImpl</code> class implements the {@link SimpleGoal} interface.
 *
 * @author Christopher Zhong
 * @since 3.2
 */
public class SimpleGoalImpl implements SimpleGoal {

	/**
	 * A mapping for <code>UniqueIdentifier</code> that maps to <code>SimpleGoalImpl</code> to ensure the uniqueness of <code>SimpleGoalImpl</code>.
	 */
	private static final Map<UniqueId, SimpleGoal> uniqueGoals = new HashMap<>();

	/**
	 * The unique <code>UniqueIdentifier</code> of the <code>SimpleGoalImpl</code>.
	 */
	private final UniqueId identifier;

	/**
	 * Constructs a new instance of <code>SimpleGoalImpl</code>.
	 *
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> representing the <code>SimpleGoalImpl</code>.
	 */
	protected SimpleGoalImpl(final UniqueId identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException("Parameter (identifier) cannot be null");
		}
		this.identifier = identifier;
	}

	@Override
	public final UniqueId getIdentifier() {
		return identifier;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SimpleGoal) {
			final SimpleGoal simpleGoal = (SimpleGoal) object;
			return getIdentifier().equals(simpleGoal.getIdentifier());
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

	/**
	 * Returns a <code>SimpleGoal</code> instance representing the given <code>UniqueIdentifier</code>.
	 * <p>
	 * If a new <>SimpleGoal</code> instance is not required, this method returns the existing <code>SimpleGoal</code> instance.
	 *
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the <code>SimpleGoal</code> instance.
	 * @return a <code>SimpleGoal</code> instance representing the given <code>UniqueIdentifier</code>.
	 */
	public static final SimpleGoal createSimpleGoal(final UniqueId identifier) {
		SimpleGoal simpleGoal = uniqueGoals.get(identifier);
		if (simpleGoal == null) {
			simpleGoal = new SimpleGoalImpl(identifier);
			uniqueGoals.put(identifier, simpleGoal);
		}
		return simpleGoal;
	}

}