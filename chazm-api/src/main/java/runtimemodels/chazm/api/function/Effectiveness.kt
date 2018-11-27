package runtimemodels.chazm.api.function

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.relation.Assignment

/**
 * The [Effectiveness] interface defines the APIs determining how effective an [Organization] is in
 * achieving the goals it set out to achieve.
 *
 * @author Christopher Zhong
 * @see [http://en.wikipedia.org/wiki/Organizational_effectiveness](http://en.wikipedia.org/wiki/Organizational_effectiveness)
 *
 * @since 3.2
 */
@FunctionalInterface
interface Effectiveness {
    /**
     * Returns the score of an [Organization]'s effectiveness in achieving its goals.
     *
     * @param organization an [Organization] in which to compute the effectiveness.
     * @param assignments  a [Set] of [Assignment]s that the given organization is working to achieve.
     * @return a score of the given organization's effectiveness.
     */
    fun compute(organization: Organization, assignments: Set<Assignment>): Double
}
