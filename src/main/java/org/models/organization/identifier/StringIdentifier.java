/*
 * StringIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.identifier;

import java.io.ObjectStreamException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The <code>StringIdentifier</code> class extends the {@link UniqueId} by using a {@link String} as the form of identification.
 * <p>
 * This <code>StringIdentifier</code> class is provided for easy of use only. The {@link #equals(Object)} method defaults to the {@link String#equals(Object)}
 * which is linear in the length of {@link String} . If performance is an issue, it is recommended that the {@link #equals(Object)} method is overridden to
 * provide a constant time complexity.
 *
 * @author Christopher Zhong, Scott Harmon
 * @see UniqueId
 * @since 4.0
 */
public class StringIdentifier extends UniqueId {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A static field that facilitates the creation of new instances of <code>StringIdentifier</code> should the need arrive.
	 */
	private static final Map<String, WeakReference<StringIdentifier>> STRING_IDENTIFIERS = new HashMap<>();

	/**
	 * A <code>ReentrantLock</code> to ensure thread-safe operations.
	 */
	private static final ReentrantLock lock = new ReentrantLock();

	/**
	 * A <code>String</code> that is used for identification.
	 */
	private final String identifier;

	/**
	 * Ensure that instances obtained from deserialization not remove from the <code>STRING_IDENTIFIERS</code>.
	 */
	private volatile boolean serialized = false;

	/**
	 * Constructs a new instance of <code>StringIdentifier</code>.
	 *
	 * @param identifier
	 *            the <code>String</code> used for identification.
	 */
	private StringIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	/**
	 * A factory method for returning a <code>StringIdentifier</code> based on the given <code>String</code>.
	 *
	 * @param id
	 *            the <code>String</code> used for identification.
	 * @return a <code>StringIdentifier</code> representing the given <code>String</code>.
	 */
	public static StringIdentifier getId(final String id) {
		lock.lock();
		try {
			final WeakReference<StringIdentifier> weakReference = STRING_IDENTIFIERS.get(id);
			StringIdentifier stringIdentifier = weakReference == null ? null : weakReference.get();
			if (stringIdentifier == null) {
				/*
				 * this check is required because entry itself is not removed when the weak reference is garbage collected
				 */
				stringIdentifier = new StringIdentifier(id);
				STRING_IDENTIFIERS.put(id, new WeakReference<>(stringIdentifier));
			}
			return stringIdentifier;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean equals(final Object object) {
		return this == object;
	}

	@Override
	public int hashCode() {
		return getObjectHashCode();
	}

	@Override
	public String toString() {
		return identifier;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (!serialized) {
			lock.lock();
			try {
				STRING_IDENTIFIERS.remove(identifier);
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * Properly deserialize, since we are using a singleton-esk pattern here.
	 *
	 * @return The proper <code>StringIdentifier</code>
	 * @throws ObjectStreamException
	 */
	private Object readResolve() throws ObjectStreamException {
		serialized = true;
		return getId(identifier);
	}

}
