package model.organization.event;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Pmf;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain PmfEvent} class indicates that there is an update about a {@linkplain Pmf} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class PmfEvent extends AbstractEvent {

	private static final long serialVersionUID = 4714992287654266663L;
	private final UniqueId<Pmf> id;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	PmfEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Pmf pmf) {
		super(category);
		id = pmf.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Pmf}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Pmf> getId() {
		return id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof PmfEvent) {
			final PmfEvent event = (PmfEvent) object;
			return super.equals(event) && getId().equals(event.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s, %s)", getClass().getSimpleName(), getCategory(), getId());
		}
		return toString;
	}

}
