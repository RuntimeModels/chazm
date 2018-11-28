package runtimemodels.chazm.api.relation.manager;

import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.api.id.AttributeId;
import runtimemodels.chazm.api.id.PmfId;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Moderates;

import java.util.Set;

/**
 * The {@linkplain ModeratesManager} interface defines the APIs for managing {@linkplain Moderates}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface ModeratesManager {
    /**
     * Creates a moderates relation between a {@linkplain Pmf} and an {@linkplain Attribute} in this {@linkplain ModeratesManager}.
     *
     * @param pmfId       the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
     * @param attributeId the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     */
    void addModerates(PmfId pmfId, AttributeId attributeId);

    /**
     * Returns an {@linkplain Attribute}, if it exists, that is moderated by a {@linkplain Pmf} in this {@linkplain ModeratesManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Pmf}
     * @return an {@linkplain Attribute} if it exists, <code>null</code> otherwise.
     */
    Attribute getModerates(PmfId id);

    /**
     * Returns a set of {@linkplain Pmf}s that moderates an {@linkplain Attribute} in this {@linkplain ModeratesManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     * @return a set of {@linkplain Pmf}s.
     */
    Set<Pmf> getModeratedBy(AttributeId id);

    /**
     * Removes a moderates relation between a {@linkplain Pmf} and ad {@linkplain Attribute} in this {@linkplain ModeratesManager}.
     *
     * @param pmfId       the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
     * @param attributeId the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     */
    void removeModerates(PmfId pmfId, AttributeId attributeId);

    /**
     * Removes all moderates relations from this {@linkplain ModeratesManager}.
     */
    void removeAllModerates();
}
