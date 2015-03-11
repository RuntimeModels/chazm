/*
 * UniqueIdentifierProvider.java
 *
 * Created on Dec 19, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.factory;

import org.models.organization.identifier.Identifiable;
import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain UniqueIdFactory} interface defines a factory for obtaining {@linkplain UniqueId}s.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
@FunctionalInterface
public interface UniqueIdFactory {
	/**
	 * Returns a {@linkplain UniqueId}.
	 * 
	 * @param <T>
	 *            the {@linkplain UniqueId} is based on the {@linkplain Class} type.
	 * @param id
	 *            the {@linkplain String} of the {@linkplain UniqueId}.
	 * @param type
	 *            the <code>Class</code> or a sub-<code>Class</code> of one of the entities of the Organization Model.
	 * @return a {@linkplain UniqueId}.
	 */
	<T extends Identifiable> UniqueId getUniqueId(String id, Class<T> type);
}
