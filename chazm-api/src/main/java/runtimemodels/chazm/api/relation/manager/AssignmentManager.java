package runtimemodels.chazm.api.relation.manager;

import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.id.AgentId;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Assignment;

import java.util.Collection;
import java.util.Set;

/**
 * The {@linkplain AssignmentManager} interface defines the APIs for managing {@linkplain Assignment}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface AssignmentManager {
    /**
     * Adds an {@linkplain Assignment} to this {@linkplain AssignmentManager}.
     *
     * @param assignment the {@linkplain Assignment} to add.
     */
    void addAssignment(Assignment assignment);

    /**
     * Adds a set of {@linkplain Assignment}s to this {@linkplain AssignmentManager}.
     *
     * @param assignments a set of {@linkplain Assignment} to add.
     */
    void addAssignments(Collection<Assignment> assignments);

    /**
     * Returns an {@linkplain Assignment} from this {@linkplain AssignmentManager}.
     *
     * @param id an {@linkplain UniqueId} that represents the {@linkplain Assignment}.
     * @return an {@linkplain Assignment} if it exists, <code>null</code> otherwise.
     */
    Assignment getAssignment(UniqueId<Assignment> id);

    /**
     * Returns a set of {@linkplain Assignment}s from this {@linkplain AssignmentManager}.
     *
     * @return a set of {@linkplain Assignment}s.
     */
    Set<Assignment> getAssignments();

    /**
     * Returns an {@linkplain Agent}'s set of {@linkplain Assignment}s from this {@linkplain AssignmentManager}.
     *
     * @param id an {@linkplain UniqueId} that represents the {@linkplain Agent}.
     * @return a set of {@linkplain Assignment}s.
     */
    Set<Assignment> getAssignmentsByAgent(AgentId id);

    /**
     * Removes an {@linkplain Assignment} from this {@linkplain AssignmentManager}.
     *
     * @param id an {@linkplain UniqueId} that represents the {@linkplain Assignment}.
     */
    void removeAssignment(UniqueId<Assignment> id);

    /**
     * Removes a set of {@linkplain Assignment}s from this {@linkplain AssignmentManager}.
     *
     * @param ids a set of {@linkplain UniqueId}s that represents the {@linkplain Assignment}s.
     */
    void removeAssignments(Collection<UniqueId<Assignment>> ids);

    /**
     * Removes all {@linkplain Assignment}s from this {@linkplain AssignmentManager}.
     */
    void removeAllAssignments();
}
