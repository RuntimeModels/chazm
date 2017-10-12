package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Role;

/**
 * The {@linkplain RequiresFactory} interface defines the API for constructing {@linkplain Requires} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface RequiresFactory {

    /**
     * Constructs a {@linkplain Requires}.
     *
     * @param role       the {@linkplain Role} of the {@linkplain Requires}.
     * @param capability the {@linkplain Capability} of the {@linkplain Requires}.
     * @return a {@linkplain Requires}.
     */
    Requires buildRequires(Role role, Capability capability);

}
