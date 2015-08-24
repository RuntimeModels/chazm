package io.github.chriszhong.model.organization.event;

import io.github.chriszhong.message.M;
import io.github.chriszhong.model.organization.entity.Characteristic;
import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.id.UniqueId;
import io.github.chriszhong.model.organization.relation.Contains;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

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
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	ContainsEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Contains contains) {
		super(category);
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

	@Override
	public boolean equals(final Object object) {
		if (object instanceof ContainsEvent) {
			final ContainsEvent event = (ContainsEvent) object;
			return super.equals(event) && getRoleId().equals(event.getRoleId()) && getCharacteristicId().equals(event.getCharacteristicId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getCategory(), getRoleId(), getCharacteristicId());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_2_IDS_AND_VALUE.get(super.toString(), getRoleId(), getCharacteristicId(), getValue());
		}
		return toString;
	}

}
