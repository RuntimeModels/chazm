/*
 * StringIdentifier.java
 *
 * Created on Nov 6, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.identifier;

import java.io.ObjectStreamException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The <code>StringIdentifier</code> class extends the {@link UniqueIdentifier}
 * by using a {@link String} as the form of identification.
 * <p>
 * This <code>StringIdentifier</code> class is provided for easy of use only.
 * The {@link #equals(Object)} method defaults to the
 * {@link String#equals(Object)} which is linear in the length of {@link String}
 * . If performance is an issue, it is recommended that the
 * {@link #equals(Object)} method is overridden to provide a constant time
 * complexity.
 * 
 * @author Christopher Zhong, Scott Harmon
 * @version $Revision: 1.1.4.1 $, $Date: 2011/09/19 14:26:43 $
 * @since 4.0
 * @see UniqueIdentifier
 */
public class StringIdentifier extends UniqueIdentifier {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A static field that facilitates the creation of new instances of
	 * <code>StringIdentifier</code> should the need arrive.
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
	 * Ensure that instances obtained from deserialization not remove from the
	 * <code>STRING_IDENTIFIERS</code>.
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
	 * A factory method for returning a <code>StringIdentifier</code> based on
	 * the given <code>String</code>.
	 * 
	 * @param identifier
	 *            the <code>String</code> used for identification.
	 * @return a <code>StringIdentifier</code> representing the given
	 *         <code>String</code>.
	 */
	public static StringIdentifier getIdentifier(final String identifier) {
		lock.lock();
		try {
			final WeakReference<StringIdentifier> weakReference = STRING_IDENTIFIERS
					.get(identifier);
			StringIdentifier stringIdentifier = weakReference == null ? null
					: weakReference.get();
			if (stringIdentifier == null) {
				/*
				 * this check is required because entry itself is not removed
				 * when the weak reference is garbage collected
				 */
				stringIdentifier = new StringIdentifier(identifier);
				STRING_IDENTIFIERS.put(identifier, new WeakReference<>(
						stringIdentifier));
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
		return getIdentifier(identifier);
	}

}
