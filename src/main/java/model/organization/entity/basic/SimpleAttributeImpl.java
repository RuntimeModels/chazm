/*
 * SimpleAgentImpl.java
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
 * The <code>SimpleAttributeImpl</code> class implements the
 * {@link SimpleAttribute} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.3 $, $Date: 2011/09/19 14:26:39 $
 * @see SimpleAttribute
 * @since 3.2
 */
public class SimpleAttributeImpl implements SimpleAttribute {

	/**
	 * A mapping for <code>UniqueIdentifier</code> that maps to
	 * <code>SimpleAttribute</code> to ensure the uniqueness of
	 * <code>SimpleAttribute</code>.
	 */
	private static final Map<UniqueIdentifier, SimpleAttribute> uniqueAttributes = new HashMap<>();

	/**
	 * The unique <code>UniqueIdentifier</code> representing the
	 * <code>SimpleAttribute</code>.
	 */
	private final UniqueIdentifier identifier;

	/**
	 * Constructs a new instance of <code>SimpleAttributeImpl</code>.
	 * 
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the
	 *            <code>SimpleAttribute</code>.
	 */
	protected SimpleAttributeImpl(final UniqueIdentifier identifier) {
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
		if (object instanceof SimpleAttribute) {
			final SimpleAttribute simpleAttribute = (SimpleAttribute) object;
			return getIdentifier().equals(simpleAttribute.getIdentifier());
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
	 * Returns a <code>SimpleAttribute</code> instance representing the given
	 * <code>UniqueIdentifier</code>.
	 * <p>
	 * If a new <>SimpleAttribute</code> instance is not required, this method
	 * returns the existing <code>SimpleAttribute</code> instance.
	 * 
	 * @param identifier
	 *            the unique <code>UniqueIdentifier</code> representing the
	 *            <code>SimpleAttribute</code> instance.
	 * @return a <code>SimpleAttribute</code> instance representing the given
	 *         <code>UniqueIdentifier</code>.
	 */
	public static final SimpleAttribute createSimpleAttribute(
			final UniqueIdentifier identifier) {
		SimpleAttribute simpleAttribute = uniqueAttributes.get(identifier);
		if (simpleAttribute == null) {
			simpleAttribute = new SimpleAttributeImpl(identifier);
			uniqueAttributes.put(identifier, simpleAttribute);
		}
		return simpleAttribute;
	}

}