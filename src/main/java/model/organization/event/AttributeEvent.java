package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;
import model.organization.entity.Attribute;
import model.organization.id.UniqueId;

/**
 * The {@linkplain AttributeEvent} class indicates that there is an update about an {@linkplain Attribute} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class AttributeEvent extends AbstractEvent {

	private static final long serialVersionUID = -2314619272565535715L;
	private final UniqueId<Attribute> id;

	/**
	 * Constructs a new instance of {@linkplain AttributeEvent}.
	 *
	 * @param attribute
	 *            the {@linkplain Attribute}.
	 * @param category
	 *            the category of the update.
	 */
	public AttributeEvent(final Attribute attribute, final UpdateCategory category) {
		super(category);
		checkNotNull(attribute, "attribute");
		id = attribute.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Attribute}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Attribute> getId() {
		return id;
	}

}
