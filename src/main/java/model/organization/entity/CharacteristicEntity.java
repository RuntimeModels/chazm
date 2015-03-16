package model.organization.entity;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class CharacteristicEntity extends AbstractEntity<Characteristic> implements Characteristic {

	@Inject
	CharacteristicEntity(@NotNull @Assisted final UniqueId<Characteristic> id) {
		super(id);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Characteristic) {
			final Characteristic characteristic = (Characteristic) object;
			return getId().equals(characteristic.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}

}
