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

import org.models.organization.identifier.UniqueIdentifier;

/**
 * The <code>SimpleCharacteristicImpl</code> class implements the
 * {@link SimpleCharacteristic} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.2 $, $Date: 2011/09/19 14:26:39 $
 * @see SimpleAttribute
 * @since 3.2
 */
public class SimpleCharacteristicImpl implements SimpleCharacteristic {

	/**
	 * A mapping for <code>UniqueIdentifier</code> that maps to
	 * <code>SimpleCharacteristic</code> to ensure the uniqueness of
	 * <code>SimpleCharacteristic</code>.
	 */
	private static final Map<UniqueIdentifier, SimpleCharacteristic> uniqueCharacteristics = new HashMap<>();

	/**
	 * The <code>UniqueIdentifier</code> representing the
	 * <code>SimpleCharacteristic</code>.
	 */
	private final UniqueIdentifier identifier;

	/**
	 * Constructs a new instance of <code>SimpleCharacteristicImpl</code>.
	 * 
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> representing the
	 *            <code>SimpleCharacteristic</code>.
	 */
	protected SimpleCharacteristicImpl(final UniqueIdentifier identifier) {
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
		if (object instanceof SimpleCharacteristic) {
			final SimpleCharacteristic simpleCharacteristic = (SimpleCharacteristic) object;
			return getIdentifier().equals(simpleCharacteristic.getIdentifier());
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
	 * Returns a <code>SimpleCharacteristic</code> instance representing the
	 * given <code>UniqueIdentifier</code>.
	 * <p>
	 * If a new <>SimpleCharacteristic</code> instance is not required, this
	 * method returns the existing <code>SimpleCharacteristic</code> instance.
	 * 
	 * @param identifier
	 *            the <code>UniqueIdentifier</code> representing the
	 *            <code>SimpleCharacteristic</code> instance.
	 * @return a <code>SimpleCharacteristic</code> instance representing the
	 *         given <code>UniqueIdentifier</code>.
	 */
	public static final SimpleCharacteristic createSimpleCharacteristic(
			final UniqueIdentifier identifier) {
		SimpleCharacteristic simpleCharacteristic = uniqueCharacteristics
				.get(identifier);
		if (simpleCharacteristic == null) {
			simpleCharacteristic = new SimpleCharacteristicImpl(identifier);
			uniqueCharacteristics.put(identifier, simpleCharacteristic);
		}
		return simpleCharacteristic;
	}

}