package runtimemodels.chazm.model.event;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Requires;
import runtimemodels.chazm.model.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The {@linkplain RequiresEvent} class indicates that there is an update about a {@linkplain Requires} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class RequiresEvent extends AbstractEvent {

    private static final long serialVersionUID = -8416757195713372958L;
    private final UniqueId<Role> roleId;
    private final UniqueId<Capability> capabilityId;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    RequiresEvent(@NotNull @Assisted final EventType category, @NotNull @Assisted final Requires requires) {
        super(category);
        roleId = requires.getRole().getId();
        capabilityId = requires.getCapability().getId();
    }

    /**
     * Returns a {@linkplain UniqueId} that represents a {@linkplain Role}.
     *
     * @return a {@linkplain UniqueId}.
     */
    public UniqueId<Role> getRoleId() {
        return roleId;
    }

    /**
     * Returns a {@linkplain UniqueId} that represents a {@linkplain Capability}.
     *
     * @return a {@linkplain UniqueId}.
     */
    public UniqueId<Capability> getCapabilityId() {
        return capabilityId;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof RequiresEvent) {
            final RequiresEvent event = (RequiresEvent) object;
            return super.equals(event) && getRoleId().equals(event.getRoleId()) && getCapabilityId().equals(event.getCapabilityId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getCategory(), getRoleId(), getCapabilityId());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.EVENT_WITH_2_IDS.get(super.toString(), getRoleId(), getCapabilityId());
        }
        return toString;
    }

}
