package io.github.runtimemodels.chazm.event;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.message.M;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Has;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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
            hashCode = Objects.hash(getCategory(), getAgentId(), getAttributeId());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.EVENT_WITH_2_IDS_AND_VALUE.get(super.toString(), getAgentId(), getAttributeId(), getValue());
        }
        return toString;
    }

}
