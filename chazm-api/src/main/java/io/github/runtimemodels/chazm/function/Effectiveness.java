package io.github.runtimemodels.chazm.function;

import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.relation.Assignment;

import java.util.Collection;

/**
 * The {@linkplain Effectiveness} interface defines the APIs determining how effective an {@linkplain Organization} is in achieving the goals it set out to
 * achieve.
 *
 * @author Christopher Zhong
 * @see <a href="http://en.wikipedia.org/wiki/Organizational_effectiveness">http://en.wikipedia.org/wiki/Organizational_effectiveness</a>
 * @since 3.2
 */
@FunctionalInterface
public interface Effectiveness {

    /**
     * Returns a <code>double</code>-valued score of an {@linkplain Organization}'s effectiveness.
     *
     * @param organization the {@linkplain Organization}.
     * @param assignments  a set of {@linkplain Assignment}s.
     * @return a <code>double</code>-valued score.
     */
    double compute(Organization organization, Collection<Assignment> assignments);

}
