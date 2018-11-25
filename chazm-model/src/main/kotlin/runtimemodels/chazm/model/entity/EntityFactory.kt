package runtimemodels.chazm.model.entity

/**
 * The [EntityFactory] interface defines the APIs for constructing entities.
 *
 * @author Christopher Zhong
 * @see AgentFactory
 *
 * @see AttributeFactory
 *
 * @see CapabilityFactory
 *
 * @see CharacteristicFactory
 *
 * @see InstanceGoalFactory
 *
 * @see PmfFactory
 *
 * @see PolicyFactory
 *
 * @see RoleFactory
 *
 * @see SpecificationGoalFactory
 *
 * @since 7.0.0
 */
interface EntityFactory : AgentFactory, AttributeFactory, CapabilityFactory, CharacteristicFactory, InstanceGoalFactory, PmfFactory, PolicyFactory, RoleFactory, SpecificationGoalFactory
