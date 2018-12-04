package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.organization.Organization

/**
 * The [InstanceGoal] interface defines an instance goal, which is a concrete instantiation
 * of a [SpecificationGoal], of an [Organization].
 *
 * @author Christopher Zhong
 * @see [SpecificationGoal]
 * @since 3.4
 */
interface InstanceGoal : Identifiable<InstanceGoal, InstanceGoalId> {
    /**
     * The [GoalParameter] interface defines a wrapper for a [Map] of [Any].
     */
    interface GoalParameter : Map<Any, Any> {
        /**
         * Adds the given `key` and `value` as a parameter to this [InstanceGoal].
         *
         * @param key   an [Any] representing the `key`.
         * @param value an [Any] representing the `value`.
         */
        fun add(key: Any, value: Any)

        /**
         * Removes a parameter by the given `key` from this [InstanceGoal].
         *
         * @param key an [Any] representing the `key`.
         */
        fun remove(key: Any)
    }

    /**
     * The [SpecificationGoal] that represents this [InstanceGoal].
     */
    val goal: SpecificationGoal

    /**
     * A [GoalParameter] that represents the parameters of this [InstanceGoal].
     */
    val parameters: GoalParameter
}
