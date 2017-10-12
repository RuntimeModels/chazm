package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.id.Identifiable;

/**
 * The {@linkplain Assignment} interface defines the assignment relation, which means that an {@linkplain Agent} is assigned to play a {@linkplain Role} to
 * achieve an {@linkplain InstanceGoal}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @see Agent
 * @see Role
 * @see InstanceGoal
 * @since 7.0.0
 */
public interface Assignment extends Identifiable<Assignment> {
    /**
     * Returns the {@linkplain Agent} of this {@linkplain Assignment}.
     *
     * @return the {@linkplain Agent} of this {@linkplain Assignment}.
     */
    Agent getAgent();

    /**
     * Returns the {@linkplain Role} of this {@linkplain Assignment}.
     *
     * @return the {@linkplain Role} of this {@linkplain Assignment}.
     */
    Role getRole();

    /**
     * Returns the {@linkplain InstanceGoal} of this {@linkplain Assignment}.
     *
     * @return the {@linkplain InstanceGoal} of this {@linkplain Assignment}.
     */
    InstanceGoal getGoal();
}
