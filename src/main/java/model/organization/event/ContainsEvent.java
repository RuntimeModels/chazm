package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.validation.constraints.NotNull;

import model.organization.entity.Characteristic;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Contains;

/**
 * The {@linkplain ContainsEvent} class indicates that there is an update about a {@linkplain Contains} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class ContainsEvent extends AbstractEvent {

	private static final long serialVersionUID = 87203168183879944L;
	private final UniqueId<Role> roleId;
	private final UniqueId<Characteristic> characteristicId;
	private final double value;

	/**
	 * Constructs a new instance of {@linkplain ContainsEvent}.
	 *
	 * @param contains
	 *            the {@linkplain Contains}.
	 * @param category
	 *            the category of the update.
	 */
	public ContainsEvent(@NotNull final Contains contains, @NotNull final UpdateCategory category) {
		super(category);
		checkNotNull(contains, "contains");
		roleId = contains.getRole().getId();
		characteristicId = contains.getCharacteristic().getId();
		value = contains.getValue();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Role}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Role> getRoleId() {
		return roleId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Characteristic}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Characteristic> getCharacteristicId() {
		return characteristicId;
	}

	/**
	 * Returns a <code>double</code> value.
	 *
	 * @return a <code>double</code> value
	 */
	public double getValue() {
		return value;
	}

}
