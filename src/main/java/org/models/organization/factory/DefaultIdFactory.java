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
 * The {@linkplain DefaultIdFactory} class is an implementation of the {@linkplain UniqueIdFactory}.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public class DefaultIdFactory implements UniqueIdFactory {
	@Override
	public <T, U> UniqueId<T> buildId(final Class<T> type, final U id) {
		return new StringId<>(type, id.toString());
	}
}
