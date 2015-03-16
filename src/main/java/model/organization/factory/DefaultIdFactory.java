/*
 * DefaultUniqueIdentifierProvider.java
 *
 * Created on Jan 8, 2008
 *
 * See License.txt file the license agreement.
 */
package model.organization.factory;

import model.organization.id.UniqueId;
import model.organization.id.UniqueIdFactory;
import model.organization.ids.StringId;

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
