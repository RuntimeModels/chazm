/*
 * SimpleCapabilityImpl.java
 * 
 * Created on Mar 6, 2007
 * 
 * See License.txt file the license agreement.
 */
package model.organization.entity.basic;

import java.util.HashMap;
import java.util.Map;

import model.organization.identifier.UniqueIdentifier;

/**
 * The <code>SimpleCapabilityImpl</code> class implements the the
 * {@link SimpleCapability} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.4.2.1 $, $Date: 2011/09/19 14:26:39 $
 * @since 3.2
 */
public class SimpleCapabilityImpl implements SimpleCapability {

	/**
	 * A mapping for <code>UniqueIdentifier</code> that maps to
	 * <code>SimpleCapability</code> to ensure the uniqueness of
	 * <code>SimpleCapability</code>.
	 */
	private static final Map<UniqueIdentifier, SimpleCapability> uniqueCapabilities = new HashMap<>();

	/**
	 * The unique <code>UniqueIdentifier</code> representing this
	 * <code>SimpleCapabilityImpl</code>.
	 */
	private final UniqueIdentifier identifier;

	/**
	 * Constructs a new instance of <code>SimpleCapabilityImpl</code>.
	 * 
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing this
	 *            <code>SimpleCapabilityImpl</code>.
	 */
	protected SimpleCapabilityImpl(final UniqueIdentifier identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException(
					"Parameter (identifier) cannot be null");
		}
		this.identifier = identifier;
	}

	@Override
	public final UniqueIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof SimpleCapability) {
			final SimpleCapability simpleCapability = (SimpleCapability) object;
			return getIdentifier().equals(simpleCapability.getIdentifier());
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
	 * Returns a <code>SimpleCapability</code> instance representing the given
	 * <code>UniqueIdentifier</code>.
	 * <p>
	 * If a new <>SimpleCapability</code> instance is not required, this method
	 * returns the existing <code>SimpleCapability</code> instance.
	 * 
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the
	 *            <code>SimpleCapability</code> instance.
	 * @return a <code>SimpleCapability</code> instance representing the given
	 *         <code>UniqueIdentifier</code>.
	 */
	public static final SimpleCapability createSimpleCapability(
			final UniqueIdentifier identifier) {
		SimpleCapability simpleCapability = uniqueCapabilities.get(identifier);
		if (simpleCapability == null) {
			simpleCapability = new SimpleCapabilityImpl(identifier);
			uniqueCapabilities.put(identifier, simpleCapability);
		}
		return simpleCapability;
	}

}