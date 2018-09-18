package io.github.runtimemodels.chazm.event;

import runtimemodels.chazm.api.relation.Moderates;

/**
 * The {@linkplain ModeratesEventFactory} interface defines the API for constructing {@linkplain ModeratesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface ModeratesEventFactory {

    /**
     * Constructs an {@linkplain ModeratesEvent}.
     *
     * @param category  the {@linkplain EventCategory}.
     * @param moderates the {@linkplain Moderates}.
     * @return a {@linkplain ModeratesEvent}.
     */
    ModeratesEvent build(EventCategory category, Moderates moderates);

}
