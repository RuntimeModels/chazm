package runtimemodels.chazm.api.relation;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.entity.Role;

/**
 * The {@linkplain Needs} interface defines the needs relation, where a {@linkplain Role} needs an {@linkplain Attribute}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Needs {
    /**
     * Returns the {@linkplain Role} of this {@linkplain Needs}.
     *
     * @return the {@linkplain Role} of this {@linkplain Needs}.
     */
    Role getRole();

    /**
     * Returns the {@linkplain Attribute} of this {@linkplain Needs}.
     *
     * @return the {@linkplain Attribute} of this {@linkplain Needs}.
     */
    Attribute getAttribute();
}
