package io.github.runtimemodels.chazm.entity;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.id.UniqueId;
import lombok.Getter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

class AgentEntity extends AbstractEntity<Agent> implements Agent {

    @Getter
    private final ContactInfo contactInfo;

    @Inject
    AgentEntity(@NotNull @Assisted final UniqueId<Agent> id, @NotNull @Assisted final ContactInfo contactInfo) {
        super(id);
        this.contactInfo = contactInfo;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Agent) {
            final Agent agent = (Agent) object;
            return super.equals(agent);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
