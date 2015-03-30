package model.organization.event;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.id.UniqueId;
import model.organization.relation.Has;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain HasEvent} class indicates that there is an update about a {@linkplain Has} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class HasEvent extends AbstractEvent {

	private static final long serialVersionUID = 4516793115062475658L;
	private final UniqueId<Agent> agentId;
	private final UniqueId<Attribute> attributeId;
	private final double value;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	HasEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Has has) {
		super(category);
		agentId = has.getAgent().getId();
		attributeId = has.getAttribute().getId();
		value = has.getValue();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents an {@linkplain Agent}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Agent> getAgentId() {
		return agentId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents an {@linkplain Attribute}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Attribute> getAttributeId() {
		return attributeId;
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
		if (object instanceof HasEvent) {
			final HasEvent event = (HasEvent) object;
			return super.equals(event) && getAgentId().equals(event.getAgentId()) && getAttributeId().equals(event.getAttributeId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getAgentId().hashCode();
			hashCode = prime * hashCode + getAttributeId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s, %s, %s, %s)", getClass().getSimpleName(), getCategory(), getAgentId(), getAttributeId(), getValue());
		}
		return toString;
	}

}
