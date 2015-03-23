package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Attribute;
import model.organization.entity.Pmf;
import model.organization.id.UniqueId;
import model.organization.relation.Moderates;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain ModeratesEvent} class indicates that there is an update about a {@linkplain Moderates} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class ModeratesEvent extends AbstractEvent {

	private static final long serialVersionUID = 273935856408749575L;
	private final UniqueId<Pmf> pmfId;
	private final UniqueId<Attribute> attributeId;

	@Inject
	ModeratesEvent(@NotNull @Assisted final Moderates moderates, @NotNull @Assisted final EventCategory category) {
		super(category);
		checkNotNull(moderates, "agent");
		pmfId = moderates.getPmf().getId();
		attributeId = moderates.getAttribute().getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Pmf}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Pmf> getPmfId() {
		return pmfId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents an {@linkplain Attribute}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Attribute> getAttributeId() {
		return attributeId;
	}

}
