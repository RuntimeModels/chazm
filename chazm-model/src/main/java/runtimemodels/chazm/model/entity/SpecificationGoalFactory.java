package runtimemodels.chazm.model.entity;

import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.id.UniqueId;

/**
 * The {@linkplain SpecificationGoalFactory} interface defines the APIs for constructing {@linkplain SpecificationGoal}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface SpecificationGoalFactory {

    /**
     * Constructs a {@linkplain SpecificationGoal}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
     * @return a {@linkplain SpecificationGoal}.
     */
    SpecificationGoal buildSpecificationGoal(UniqueId<SpecificationGoal> id);

}
