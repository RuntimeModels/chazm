/*
 * AttributeImpl.java
 *
 * Created on Oct 15, 2009
 *
 * See License.txt file the license agreement.
 */
package model.organization.entities;

import model.organization.NullCheck;
import model.organization.id.UniqueId;

/**
 * The {@link AttributeEntity} class is an implementation of the {@link Attribute}.
 *
 * @author Christopher Zhong
 * @see Attribute
 * @since 5.0
 */
public class AttributeEntity implements Attribute, NullCheck {
	/**
	 * The {@linkplain UniqueId} that represents this {@link Attribute}.
	 */
	private final UniqueId<Attribute> id;
	/**
	 * The {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 */
	private final Type type;

	/**
	 * Constructs a new instance of {@link Attribute}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@link Attribute}.
	 * @param type
	 *            the {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 */
	public AttributeEntity(final UniqueId<Attribute> id, final Type type) {
		checkNotNull(id, "id");
		checkNotNull(type, "type");
		this.id = id;
		this.type = type;
	}

	@Override
	public final UniqueId<Attribute> getId() {
		return id;
	}

	@Override
	public final Type getType() {
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
