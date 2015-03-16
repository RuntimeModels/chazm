package model.organization.entity;

import model.organization.id.UniqueId;

/**
 * The {@linkplain EntityFactory} interface defines the APIs for constructing entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface EntityFactory {

	/**
	 * Constructs an {@linkplain Agent}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Agent}.
	 * @param contactInfo
	 *            the {@linkplain Agent.ContactInfo} for the {@linkplain Agent}.
	 * @return an {@linkplain Agent}.
	 */
	Agent buildAgent(UniqueId<Agent> id, Agent.ContactInfo contactInfo);

	/**
	 * Constructs an {@linkplain Attribute}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 * @param type
	 *            the {@linkplain Attribute.Type} of the {@linkplain Attribute}.
	 * @return an {@linkplain Attribute}.
	 */
	Attribute buildAttribute(UniqueId<Attribute> id, Attribute.Type type);

	/**
	 * Constructs a {@linkplain Capability}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 * @return a {@linkplain Capability}.
	 */
	Capability buildCapability(UniqueId<Capability> id);

	/**
	 * Constructs a {@linkplain Characteristic}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Characteristic}.
	 * @return a {@linkplain Characteristic}.
	 */
	Characteristic buildCharacteristic(UniqueId<Characteristic> id);

	/**
	 * Constructs an {@linkplain InstanceGoal}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain InstanceGoal}.
	 * @param goal
	 *            the {@linkplain SpecificationGoal} of the {@linkplain InstanceGoal}.
	 * @param parameter
	 *            the {@linkplain InstanceGoal.Parameter} of the {@linkplain InstanceGoal}.
	 * @return an {@linkplain InstanceGoal}.
	 */
	InstanceGoal buildInstanceGoal(UniqueId<InstanceGoal> id, SpecificationGoal goal, InstanceGoal.Parameter parameter);

	/**
	 * Constructs a {@linkplain Pmf}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
	 * @return a {@linkplain Pmf}.
	 */
	Pmf buildPmf(UniqueId<Pmf> id);

	/**
	 * Constructs a {@linkplain Policy}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Policy}.
	 * @return a {@linkplain Agent}.
	 */
	Policy buildPolicy(UniqueId<Policy> id);

	/**
	 * Constructs a {@linkplain Role}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @return a {@linkplain Role}.
	 */
	Role buildRole(UniqueId<Role> id);

	/**
	 * Constructs a {@linkplain SpecificationGoal}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
	 * @return a {@linkplain SpecificationGoal}.
	 */
	SpecificationGoal buildSpecificationGoal(UniqueId<SpecificationGoal> id);

}
