package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.entity.Attribute.Type;
import io.github.runtimemodels.chazm.id.UniqueId;

/**
 * The {@linkplain AttributeFactory} interface defines the APIs for constructing {@linkplain Attribute}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface AttributeFactory {

    /**
     * Constructs an {@linkplain Attribute}.
     *
     * @param id   the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
     * @param type the {@linkplain Type} of the {@linkplain Attribute}.
     * @return an {@linkplain Attribute}.
     */
    Attribute buildAttribute(UniqueId<Attribute> id, Type type);

}
