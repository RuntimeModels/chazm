package io.github.runtimemodels.chazm.entity;

import runtimemodels.chazm.api.entity.Policy;
import runtimemodels.chazm.api.id.UniqueId;

/**
 * The {@linkplain PolicyFactory} interface defines the APIs for constructing {@linkplain Policy}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PolicyFactory {

    /**
     * Constructs a {@linkplain Policy}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Policy}.
     * @return a {@linkplain Policy}.
     */
    Policy buildPolicy(UniqueId<Policy> id);

}
