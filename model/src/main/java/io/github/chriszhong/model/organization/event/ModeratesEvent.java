package io.github.chriszhong.model.organization.event;

import io.github.chriszhong.message.M;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.chazm.relation.Moderates;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

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
			hashCode = Objects.hash(getCategory(), getPmfId(), getAttributeId());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_2_IDS.get(super.toString(), getPmfId(), getAttributeId());
		}
		return toString;
	}

}
