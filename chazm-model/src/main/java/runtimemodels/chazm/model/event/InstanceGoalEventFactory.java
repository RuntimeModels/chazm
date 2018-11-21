package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.entity.InstanceGoal;

/**
 * The {@linkplain InstanceGoalEventFactory} interface defines the API for constructing {@linkplain InstanceGoalEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface InstanceGoalEventFactory {

    /**
     * Constructs an {@linkplain InstanceGoalEvent}.
     *
     * @param category the {@linkplain EventType}.
     * @param goal     the {@linkplain InstanceGoal}.
     * @return a {@linkplain InstanceGoalEvent}.
     */
    InstanceGoalEvent build(EventType category, InstanceGoal goal);

}
