package io.github.runtimemodels.chazm.relation;

import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Needs;

/**
 * The {@linkplain NeedsFactory} interface defines the API for constructing {@linkplain Needs} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface NeedsFactory {

    /**
     * Constructs a {@linkplain Needs}.
     *
     * @param role      the {@linkplain Role} of the {@linkplain Needs}.
     * @param attribute the {@linkplain Attribute} of the {@linkplain Needs}.
     * @return a {@linkplain Needs}.
     */
    Needs buildNeeds(Role role, Attribute attribute);

}
