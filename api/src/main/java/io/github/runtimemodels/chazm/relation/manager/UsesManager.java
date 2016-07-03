package io.github.runtimemodels.chazm.relation.manager;

import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.id.UniqueId;

import java.util.Set;

/**
 * The {@linkplain UsesManager} interface defines the APIs for managing {@linkplain Pmf}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface UsesManager {
    /**
     * Creates a uses relation between a {@linkplain Role} and a {@linkplain Pmf} in this {@linkplain UsesManager}.
     *
     * @param roleId the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param pmfId  the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
     */
    void addUses(UniqueId<Role> roleId, UniqueId<Pmf> pmfId);

    /**
     * Returns a set of {@linkplain Pmf}s that is used by a {@linkplain Role} in this {@linkplain UsesManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @return a set of {@linkplain Pmf}s.
     */
    Set<Pmf> getUses(UniqueId<Role> id);

    /**
     * Returns a set of {@linkplain Role}s that uses a {@linkplain Pmf} in this {@linkplain UsesManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
     * @return a set of {@linkplain Role}s.
     */
    Set<Role> getUsedBy(UniqueId<Pmf> id);

    /**
     * Removes a uses relation between a {@linkplain Role} and a {@linkplain Pmf} from this {@linkplain UsesManager}.
     *
     * @param roleId the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param pmfId  the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
     */
    void removeUses(UniqueId<Role> roleId, UniqueId<Pmf> pmfId);

    /**
     * Removes all uses relations from this {@linkplain UsesManager}.
     */
    void removeAllUses();
}
