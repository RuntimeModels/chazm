package model.organization.relation;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Characteristic;
import model.organization.entity.Role;

import com.google.inject.assistedinject.Assisted;

class ContainsRelation implements Contains {

	private final Role role;
	private final Characteristic characteristic;
	private double value;
	private transient Integer hashCode = null;

	@Inject
	ContainsRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Characteristic characteristic, @NotNull @Assisted final double value) {
		this.role = role;
		this.characteristic = characteristic;
		this.value = value;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public Characteristic getCharacteristic() {
		return characteristic;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(final double value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Contains) {
			final Contains contains = (Contains) object;
			return getRole().equals(contains.getRole()) && getCharacteristic().equals(contains.getCharacteristic());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getRole().hashCode() << 16 | getCharacteristic().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return String.format("%s <-> %s: %f", getRole().getId(), getCharacteristic().getId(), getValue());
	}

}
