/*
 * UniqueIdentifierProvider.java
 *
 * Created on Dec 19, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.factory;

import org.models.organization.id.UniqueId;

/**
 * The {@linkplain UniqueIdFactory} interface defines the APIs for obtaining {@linkplain UniqueId}s.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
@FunctionalInterface
public interface UniqueIdFactory {
	/**
	 * Returns a {@linkplain UniqueId}.
	 *
	 * @param type
	 *            the type of the {@linkplain UniqueId}.
	 * @param id
	 *            the id of the {@linkplain UniqueId}.
	 * @return a {@linkplain UniqueId}.
	 */
	<T, U> UniqueId<T> buildId(Class<T> type, U id);
}
