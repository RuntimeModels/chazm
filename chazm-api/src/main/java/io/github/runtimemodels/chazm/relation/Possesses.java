package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Capability;

/**
 * The {@linkplain Possesses} interface defines the possesses relation, where an {@linkplain Agent} possesses a {@linkplain Capability}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Possesses {

    /**
     * Returns the {@linkplain Agent} of this {@linkplain Possesses}.
     *
     * @return the {@linkplain Agent} of this {@linkplain Possesses}.
     */
    Agent getAgent();

    /**
     * Returns the {@linkplain Capability} of this {@linkplain Possesses}.
     *
     * @return the {@linkplain Capability} of this {@linkplain Possesses}.
     */
    Capability getCapability();

    /**
     * Returns the <code>double</code> score of this {@linkplain Possesses}.
     *
     * @return the <code>double</code> score of this {@linkplain Possesses}.
     */
    double getScore();

    /**
     * Sets the <code>double</code> score of this {@linkplain Possesses}.
     *
     * @param score the new <code>double</code> score to set.
     */
    void setScore(double score);
}
