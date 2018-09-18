package io.github.runtimemodels.chazm.event;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.message.M;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.id.UniqueId;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The {@linkplain AttributeEvent} class indicates that there is an update about an {@linkplain Attribute} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class AttributeEvent extends AbstractEvent {

    private static final long serialVersionUID = -2314619272565535715L;
    private final UniqueId<Attribute> id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    AttributeEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Attribute attribute) {
        super(category);
        id = attribute.getId();
    }

    /**
     * Returns a {@linkplain UniqueId} that represents a {@linkplain Attribute}.
     *
     * @return a {@linkplain UniqueId}.
     */
    public UniqueId<Attribute> getId() {
        return id;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof AttributeEvent) {
            final AttributeEvent event = (AttributeEvent) object;
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
