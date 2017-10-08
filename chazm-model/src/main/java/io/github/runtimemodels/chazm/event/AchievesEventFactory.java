package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.relation.Achieves;

/**
 * The {@linkplain AchievesEventFactory} interface defines the API for constructing {@linkplain AchievesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AchievesEventFactory {

    /**
     * Constructs an {@linkplain AchievesEvent}.
     *
     * @param category the {@linkplain EventCategory}.
     * @param achieves the {@linkplain Achieves}.
     * @return an {@linkplain AchievesEvent}.
     */
    AchievesEvent build(EventCategory category, Achieves achieves);

}
