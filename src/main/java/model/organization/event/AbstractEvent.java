package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import java.io.Serializable;

/**
 * The {@linkplain AbstractEvent} class provides an easier way to implement events as they all have the category.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public abstract class AbstractEvent implements Serializable {

	private static final long serialVersionUID = 3392050291256215349L;
	private final UpdateCategory category;

	/**
	 * Constructs a new instance of {@linkplain AbstractEvent}.
	 *
	 * @param category
	 *            the category of the update.
	 */
	protected AbstractEvent(final UpdateCategory category) {
		checkNotNull(category, "category");
		this.category = category;
	}

	/**
	 * Returns the category of the update.
	 *
	 * @return the category.
	 */
	public UpdateCategory getCategory() {
		return category;
	}

}