package runtimemodels.chazm.model.relation;

import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Requires;

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
