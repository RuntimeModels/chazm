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
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	ModeratesEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Moderates moderates) {
		super(category);
		checkNotNull(moderates, "moderates");
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

	@Override
	public boolean equals(final Object object) {
		if (object instanceof ModeratesEvent) {
			final ModeratesEvent event = (ModeratesEvent) object;
			return super.equals(event) && getPmfId().equals(event.getPmfId()) && getAttributeId().equals(event.getAttributeId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getPmfId().hashCode();
			hashCode = prime * hashCode + getAttributeId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s, %s, %s)", getClass().getSimpleName(), getCategory(), getPmfId(), getAttributeId());
		}
		return toString;
	}

}
