package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Attribute;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain AttributeEvent} class indicates that there is an update about an {@linkplain Attribute} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class AttributeEvent extends AbstractEvent {

	private static final long serialVersionUID = -2314619272565535715L;
	private final UniqueId<Attribute> id;

	@Inject
	AttributeEvent(@NotNull @Assisted final Attribute attribute, @NotNull @Assisted final EventCategory category) {
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
