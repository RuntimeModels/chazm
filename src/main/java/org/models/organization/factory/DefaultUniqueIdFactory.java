/*
 * DefaultUniqueIdentifierProvider.java
 *
 * Created on Jan 8, 2008
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.factory;

import org.models.organization.id.StringId;
import org.models.organization.id.UniqueId;

/**
 * The {@linkplain DefaultUniqueIdFactory} class is an implementation of the {@linkplain UniqueIdFactory}.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public class DefaultUniqueIdFactory implements UniqueIdFactory {
	@Override
	public <T> UniqueId<T> buildId(final String id, final Class<T> type) {
		return new StringId<T>(id);
	}
}
