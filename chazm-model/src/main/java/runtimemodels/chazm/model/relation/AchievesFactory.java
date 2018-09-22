package runtimemodels.chazm.model.relation;

import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.relation.Achieves;

/**
 * The {@linkplain AchievesFactory} interface defines the API for constructing {@linkplain Achieves} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AchievesFactory {

    /**
     * Constructs an {@linkplain Achieves}.
     *
     * @param role the {@linkplain Role} of the {@linkplain Achieves}.
     * @param goal the {@linkplain Achieves} of the {@linkplain Achieves}.
     * @return an {@linkplain Achieves}.
     */
    Achieves buildAchieves(Role role, SpecificationGoal goal);

}
