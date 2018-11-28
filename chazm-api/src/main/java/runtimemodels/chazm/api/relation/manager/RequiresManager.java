package runtimemodels.chazm.api.relation.manager;

import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.id.CapabilityId;
import runtimemodels.chazm.api.id.RoleId;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Requires;

import java.util.Set;

/**
 * The {@linkplain RequiresManager} interface defines the APIs for managing the {@linkplain Requires}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface RequiresManager {
    /**
     * Creates a requires relation between a {@linkplain Role} and a {@linkplain Capability} in this {@linkplain RequiresManager}.
     *
     * @param roleId       the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param capabilityId the {@linkplain UniqueId} that represents the {@linkplain Capability}.
     */
    void addRequires(RoleId roleId, CapabilityId capabilityId);

    /**
     * Returns a set of {@linkplain Capability}s that is required by a {@linkplain Role} in this {@linkplain RequiresManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @return the set of {@linkplain Capability}s.
     */
    Set<Capability> getRequires(RoleId id);

    /**
     * Returns a set of {@linkplain Role}s that requires a {@linkplain Capability} in this {@linkplain RequiresManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Capability}.
     * @return a set of {@linkplain Role}s.
     */
    Set<Role> getRequiredBy(CapabilityId id);

    /**
     * Removes a requires relation between a {@linkplain Role} and a {@linkplain Capability} from this {@linkplain RequiresManager}.
     *
     * @param roleId       the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param capabilityId the {@linkplain UniqueId} that represents the {@linkplain Capability}.
     */
    void removeRequires(RoleId roleId, CapabilityId capabilityId);

    /**
     * Removes all requires relations from this {@linkplain RequiresManager}.
     */
    void removeAllRequires();
}
