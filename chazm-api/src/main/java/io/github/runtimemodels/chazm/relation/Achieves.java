package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;

/**
 * The {@linkplain Achieves} interface defines the achieves relation, which means that a {@linkplain Role} achieves a {@linkplain SpecificationGoal}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @see Role
 * @see SpecificationGoal
 * @since 7.0.0
 */
public interface Achieves {
    /**
     * Returns the {@linkplain Role} of this {@linkplain Achieves}.
     *
     * @return the {@linkplain Role} of this {@linkplain Achieves}.
     */
    Role getRole();

    /**
     * Returns the {@linkplain SpecificationGoal} of this {@linkplain Achieves}.
     *
     * @return the {@linkplain SpecificationGoal} of this {@linkplain Achieves}.
     */
    SpecificationGoal getGoal();
}
