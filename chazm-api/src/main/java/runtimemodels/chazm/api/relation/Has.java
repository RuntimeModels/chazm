package runtimemodels.chazm.api.relation;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.Attribute;

/**
 * The {@linkplain Has} interface defines the has relation, where an {@linkplain Agent} has an {@linkplain Attribute}, of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Has {

    /**
     * Returns the {@linkplain Agent} of this {@linkplain Has}.
     *
     * @return the {@linkplain Agent} of this {@linkplain Has}.
     */
    Agent getAgent();

    /**
     * Returns the {@linkplain Attribute} of this {@linkplain Has}.
     *
     * @return the {@linkplain Attribute} of this {@linkplain Has}.
     */
    Attribute getAttribute();

    /**
     * Returns the <code>double</code> value of this {@linkplain Has}.
     *
     * @return the <code>double</code> value of this {@linkplain Has}.
     */
    double getValue();

    /**
     * Sets a new <code>double</code> value for this {@linkplain Has}.
     *
     * @param value the <code>double</code> value to set.
     */
    void setValue(double value);

}
