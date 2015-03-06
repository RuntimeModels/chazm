/*
 * EventRegistry.java
 *
 * Created on Sep 14, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.registry;

/**
 * The <code>EventRegistry</code> class is a mechanism for implementations of {@link ChangeManager} to provide an actual reference that can be used.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public final class EventRegistry {

	/**
	 * The static reference to an instance of <code>ChangeManager</code>.
	 */
	private static ChangeManager changeManager = null;

	/**
	 * Sets the <code>ChangeManager</code>.
	 *
	 * @param changeManager
	 *            the <code>ChangeManager</code> to set.
	 */
	public static void set(final ChangeManager changeManager) {
		EventRegistry.changeManager = changeManager;
	}

	/**
	 * Gets the <code>ChangeManager</code>.
	 *
	 * @return the <code>ChangeManager</code>.
	 */
	public static ChangeManager get() {
		return changeManager;
	}

}
