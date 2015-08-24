package io.github.chriszhong.model.organization.entity;

/**
 * The {@linkplain EntityFactory} interface defines the APIs for constructing entities.
 *
 * @author Christopher Zhong
 * @see AgentFactory
 * @see AttributeFactory
 * @see CapabilityFactory
 * @see CharacteristicFactory
 * @see InstanceGoalFactory
 * @see PmfFactory
 * @see PolicyFactory
 * @see RoleFactory
 * @see SpecificationGoalFactory
 * @since 7.0.0
 */
public interface EntityFactory extends AgentFactory, AttributeFactory, CapabilityFactory, CharacteristicFactory, InstanceGoalFactory, PmfFactory,
		PolicyFactory, RoleFactory, SpecificationGoalFactory {}
