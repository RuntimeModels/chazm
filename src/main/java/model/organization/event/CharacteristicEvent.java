package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Characteristic;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain CharacteristicEvent} class indicates that there is an update about a {@linkplain Characteristic} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class CharacteristicEvent extends AbstractEvent {

	private static final long serialVersionUID = -7464874692386039815L;
	private final UniqueId<Characteristic> id;

	@Inject
	CharacteristicEvent(@NotNull @Assisted final Characteristic characteristic, @NotNull @Assisted final EventCategory category) {
		super(category);
		checkNotNull(characteristic, "characteristic");
		id = characteristic.getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Characteristic}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Characteristic> getId() {
		return id;
	}

}
