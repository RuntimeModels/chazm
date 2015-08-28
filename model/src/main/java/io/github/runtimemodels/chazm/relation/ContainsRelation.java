package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.relation.Contains;
import io.github.runtimemodels.message.M;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

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
			hashCode = Objects.hash(getRole(), getCharacteristic());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return M.RELATION_WITH_VALUE.get(getRole().getId(), getCharacteristic().getId(), getValue());
	}

}
