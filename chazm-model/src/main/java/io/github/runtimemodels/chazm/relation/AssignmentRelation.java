package io.github.runtimemodels.chazm.relation;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.id.AbstractId;
import io.github.runtimemodels.message.M;
import lombok.Getter;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.InstanceGoal;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Assignment;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class AssignmentRelation implements Assignment {
    /**
     * The {@linkplain Id} extends the {@linkplain AbstractId} by using three {@linkplain UniqueId}s; the {@linkplain UniqueId} of an {@linkplain Agent}, the
     * {@linkplain UniqueId} of a {@linkplain Role}, and the {@linkplain UniqueId} of an {@linkplain InstanceGoal}.
     *
     * @author Christopher Zhong
     * @see UniqueId
     * @since 4.0
     */
    public static class Id extends AbstractId<Assignment> {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 7696865567559985410L;

        /**
         * The {@linkplain Id} that represents an {@linkplain Agent}.
         */
        private final UniqueId<Agent> agentId;

        /**
         * The {@linkplain Id} that represents a {@linkplain Role}.
         */
        private final UniqueId<Role> roleId;

        /**
         * The {@linkplain Id} that represents an {@linkplain InstanceGoal}.
         */
        private final UniqueId<InstanceGoal> goalId;

        /**
         * Optimization for hash code computation since it never changes.
         */
        private transient Integer hashCode = null;

        /**
         * Optimization for <code>toString</code> method since it never changes.
         */
        private transient String toString = null;

        /**
         * Constructs a new instance of {@linkplain Assignment}.
         *
         * @param agentId the {@linkplain Id} that represents an {@linkplain Agent}.
         * @param roleId  the {@linkplain Id} that represents a {@linkplain Role}.
         * @param goalId  the {@linkplain Id} that represents an {@linkplain InstanceGoal}.
         */
        public Id(final UniqueId<Agent> agentId, final UniqueId<Role> roleId, final UniqueId<InstanceGoal> goalId) {
            super(Assignment.class);
            this.agentId = agentId;
            this.roleId = roleId;
            this.goalId = goalId;
        }

        /**
         * Returns the {@linkplain Id} that represents an {@linkplain Agent}.
         *
         * @return the {@linkplain Id} that represents an {@linkplain Agent}.
         */
        private UniqueId<Agent> getAgentId() {
            return agentId;
        }

        /**
         * Returns the {@linkplain Id} that represents a {@linkplain Role}.
         *
         * @return the {@linkplain Id} that represents a {@linkplain Role}.
         */
        private UniqueId<Role> getRoleId() {
            return roleId;
        }

        /**
         * Returns the {@linkplain Id} that represents an {@linkplain InstanceGoal}.
         *
         * @return the {@linkplain Id} that represents an {@linkplain InstanceGoal}.
         */
        private UniqueId<InstanceGoal> getGoalId() {
            return goalId;
        }

        @Override
        public boolean equals(final Object object) {
            if (object instanceof Id) {
                final Id id = (Id) object;
                return getAgentId().equals(id.getAgentId()) && getRoleId().equals(id.getRoleId()) && getGoalId().equals(id.getGoalId());
            }
            return false;
        }

        @Override
        public int hashCode() {
            if (hashCode == null) {
                hashCode = Objects.hash(super.hashCode(), getAgentId(), getRoleId(), getGoalId());
            }
            return hashCode;
        }

        @Override
        public String toString() {
            if (toString == null) {
                toString = M.ASSIGNMENT_ID.get(getClass().getSimpleName(), getAgentId(), getRoleId(), getGoalId());
            }
            return toString;
        }
    }

    @Getter
    private final UniqueId<Assignment> id;
    @Getter
    private final Agent agent;
    @Getter
    private final Role role;
    @Getter
    private final InstanceGoal goal;
    private transient String toString = null;

    @Inject
    AssignmentRelation(@NotNull @Assisted final Agent agent, @NotNull @Assisted final Role role, @NotNull @Assisted final InstanceGoal goal) {
        id = new Id(agent.getId(), role.getId(), goal.getId());
        this.agent = agent;
        this.role = role;
        this.goal = goal;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Assignment) {
            final Assignment assignment = (Assignment) object;
            return getId().equals(assignment.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.ASSIGNMENT.get(getAgent().getId(), getRole().getId(), getGoal().getId());
        }
        return toString;
    }

}
