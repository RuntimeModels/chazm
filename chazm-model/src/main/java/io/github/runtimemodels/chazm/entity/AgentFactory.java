package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.entity.Agent.ContactInfo;
import io.github.runtimemodels.chazm.id.UniqueId;

/**
 * The {@linkplain AgentFactory} interface defines the APIs for constructing {@linkplain Agent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AgentFactory {

    /**
     * Constructs an {@linkplain Agent}.
     *
     * @param id          the {@linkplain UniqueId} that represents the {@linkplain Agent}.
     * @param contactInfo the {@linkplain ContactInfo} for the {@linkplain Agent}.
     * @return an {@linkplain Agent}.
     */
    Agent buildAgent(UniqueId<Agent> id, ContactInfo contactInfo);

}
