package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.id.Identifiable

/**
 * The [SpecificationGoal] interface defines a specification goal entity of an [Organization].
 *
 * A [SpecificationGoal] is a form marker to differentiate between different types of goal.
 * Within an [Organization], there can be multiple entities that are trying to achieve the same goal.
 * This common can be represented as a [SpecificationGoal] with one instance for each entity that is
 * trying to achieve it.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
interface SpecificationGoal : Identifiable<SpecificationGoal>
