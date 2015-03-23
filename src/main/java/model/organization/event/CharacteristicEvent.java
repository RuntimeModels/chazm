package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.validation.constraints.NotNull;

import model.organization.entity.Characteristic;
import model.organization.id.UniqueId;

/**
 * The {@linkplain CharacteristicEvent} class indicates that there is an update about a {@linkplain Characteristic} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class CharacteristicEvent extends AbstractEvent {

	private static final long serialVersionUID = -7464874692386039815L;
	private final UniqueId<Characteristic> id;

	/**
	 * Constructs a new instance of {@linkplain CharacteristicEvent}.
	 *
	 * @param characteristic
	 *            the {@linkplain Characteristic}.
	 * @param category
	 *            the category of the update.
	 */
	public CharacteristicEvent(@NotNull final Characteristic characteristic, @NotNull final UpdateCategory category) {
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
