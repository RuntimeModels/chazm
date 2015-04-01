package model.organization.event;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import message.M;

/**
 * The {@linkplain AbstractEvent} class provides an easier way to implement events as they all have the category.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public abstract class AbstractEvent implements Serializable {

	private static final long serialVersionUID = 3392050291256215349L;
	private final EventCategory category;
	private transient Integer hashCode = null;
	private transient String toString = null;

	/**
	 * Constructs a new instance of {@linkplain AbstractEvent}.
	 *
	 * @param category
	 *            the category of the update.
	 */
	protected AbstractEvent(@NotNull final EventCategory category) {
		this.category = category;
	}

	/**
	 * Returns the category of the update.
	 *
	 * @return the category.
	 */
	public EventCategory getCategory() {
		return category;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof AbstractEvent) {
			final AbstractEvent event = (AbstractEvent) object;
			return getCategory().equals(event.getCategory());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = category.hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT.get(getClass().getSimpleName(), category);
		}
		return toString;
	}

}