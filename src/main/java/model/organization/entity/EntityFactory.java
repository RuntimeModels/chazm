package model.organization.entity;

/**
 * The {@linkplain EntityFactory} interface defines the APIs for constructing entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface EntityFactory extends AgentFactory, AttributeFactory, CapabilityFactory, CharacteristicFactory, InstanceGoalFactory, PmfFactory,
		PolicyFactory, RoleFactory, SpecificationGoalFactory {}
