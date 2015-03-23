package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

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

	@Inject
	PmfEvent(@NotNull @Assisted final Pmf pmf, @NotNull @Assisted final EventCategory category) {
		super(category);
		checkNotNull(pmf, "pmf");
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

}
