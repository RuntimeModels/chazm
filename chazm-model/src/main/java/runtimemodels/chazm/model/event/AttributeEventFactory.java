package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.entity.Attribute;

/**
 * The {@linkplain AttributeEventFactory} interface defines the API for constructing {@linkplain AttributeEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AttributeEventFactory {

    /**
     * Constructs an {@linkplain AttributeEvent}.
     *
     * @param category  the {@linkplain EventType}.
     * @param attribute the {@linkplain Attribute}.
     * @return a {@linkplain AttributeEvent}.
     */
    AttributeEvent build(EventType category, Attribute attribute);

}
