package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

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

	@Inject
	HasEvent(@NotNull @Assisted final Has has, @NotNull @Assisted final EventCategory category) {
		super(category);
		checkNotNull(has, "has");
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

}
