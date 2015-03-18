package model.organization.entity;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

class AttributeEntity extends AbstractEntity<Attribute> implements Attribute {

	private final Attribute.Type type;

	@Inject
	AttributeEntity(@NotNull @Assisted final UniqueId<Attribute> id, @NotNull @Assisted final Attribute.Type type) {
		super(id);
		checkNotNull(type, "type");
		this.type = type;
	}

	@Override
	public final Attribute.Type getType() {
		return type;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Attribute) {
			final Attribute attribute = (Attribute) object;
			return getId().equals(attribute.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", getId().toString(), getType());
	}

}
