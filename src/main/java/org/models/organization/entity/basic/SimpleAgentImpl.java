/*
 * SimpleAgentImpl.java
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
 * The <code>SimpleAgentImpl</code> class implements the {@link SimpleAgent} interface.
 *
 * @author Christopher Zhong
 * @see SimpleAgent
 * @since 3.2
 */
public class SimpleAgentImpl implements SimpleAgent {

	/**
	 * A mapping for <code>UniqueIdentifier</code> that maps to <code>SimpleAgent</code> to ensure the uniqueness of <code>SimpleAgent</code>.
	 */
	private static final Map<UniqueId, SimpleAgent> uniqueAgents = new HashMap<>();

	/**
	 * The unique <code>UniqueIdentifier</code> representing the <code>SimpleAgentImpl</code>.
	 */
	private final UniqueId identifier;

	/**
	 * Constructs a new instance of <code>SimpleAgentImpl</code>.
	 *
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the <code>SimpleAgentImpl</code>.
	 */
	protected SimpleAgentImpl(final UniqueId identifier) {
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
		if (object instanceof SimpleAgent) {
			final SimpleAgent simpleAgent = (SimpleAgent) object;
			return getIdentifier().equals(simpleAgent.getIdentifier());
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
	 * Returns a <code>SimpleAgent</code> instance representing the given <code>UniqueIdentifier</code>.
	 * <p>
	 * If a new <>SimpleAgent</code> instance is not required, this method returns the existing <code>SimpleAgent</code> instance.
	 *
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the <code>SimpleAgent</code> instance.
	 * @return a <code>SimpleAgent</code> instance representing the given <code>UniqueIdentifier</code>.
	 */
	public static final SimpleAgent createSimpleAgent(final UniqueId identifier) {
		SimpleAgent simpleAgent = uniqueAgents.get(identifier);
		if (simpleAgent == null) {
			simpleAgent = new SimpleAgentImpl(identifier);
			uniqueAgents.put(identifier, simpleAgent);
		}
		return simpleAgent;
	}

}