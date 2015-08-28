package io.github.chriszhong.model.organization.event;

import io.github.chriszhong.message.M;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.id.UniqueId;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

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
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	CharacteristicEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Characteristic characteristic) {
		super(category);
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

	@Override
	public boolean equals(final Object object) {
		if (object instanceof CharacteristicEvent) {
			final CharacteristicEvent event = (CharacteristicEvent) object;
			return super.equals(event) && getId().equals(event.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = Objects.hash(getCategory(), getId());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = M.EVENT_WITH_1_ID.get(super.toString(), getId());
		}
		return toString;
	}

}
