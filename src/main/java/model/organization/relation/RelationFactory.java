package model.organization.relation;

import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Capability;
import model.organization.entity.Characteristic;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Pmf;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;

/**
 * The {@linkplain RelationFactory} interface defines the APIs for relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface RelationFactory {

	/**
	 * Constructs an {@linkplain Achieves}.
	 *
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Achieves}.
	 * @param goal
	 *            the {@linkplain Achieves} of the {@linkplain Achieves}.
	 * @return an {@linkplain Achieves}.
	 */
	Achieves buildAchieves(Role role, SpecificationGoal goal);

	/**
	 * Constructs an {@linkplain Assignment}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of the {@linkplain Assignment}.
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Assignment}.
	 * @param goal
	 *            the {@linkplain InstanceGoal} of the {@linkplain Assignment}.
	 * @return an {@linkplain Assignment}.
	 */
	Assignment buildAssignment(Agent agent, Role role, InstanceGoal goal);

	/**
	 * Constructs a {@linkplain Contains}.
	 *
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Contains}.
	 * @param characteristic
	 *            the {@linkplain Characteristic} of the {@linkplain Contains}.
	 * @param value
	 *            the <code>double</code> value of the {@linkplain Contains}.
	 * @return a {@linkplain Contains}.
	 */
	Contains buildContains(Role role, Characteristic characteristic, double value);

	/**
	 * Constructs a {@linkplain Has}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of the {@linkplain Has}.
	 * @param attribute
	 *            the {@linkplain Attribute} of the {@linkplain Has}.
	 * @param value
	 *            the <code>double</code> value of the {@linkplain Has}.
	 * @return a {@linkplain Has}.
	 */
	Has buildHas(Agent agent, Attribute attribute, double value);

	/**
	 * Constructs a {@linkplain Moderates}.
	 *
	 * @param pmf
	 *            the {@linkplain Pmf} of the {@linkplain Moderates}.
	 * @param attribute
	 *            the {@linkplain Attribute} of the {@linkplain Moderates}.
	 * @return a {@linkplain Moderates}.
	 */
	Moderates buildModerates(Pmf pmf, Attribute attribute);

	/**
	 * Constructs a {@linkplain Needs}.
	 *
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Needs}.
	 * @param attribute
	 *            the {@linkplain Attribute} of the {@linkplain Needs}.
	 * @return a {@linkplain Needs}.
	 */
	Needs buildNeeds(Role role, Attribute attribute);

	/**
	 * Constructs a {@linkplain Possesses}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of the {@linkplain Possesses}.
	 * @param capability
	 *            the {@linkplain Capability} of the {@linkplain Possesses}.
	 * @param score
	 *            the <code>double</code> score of the {@linkplain Possesses}.
	 * @return a {@linkplain Possesses}.
	 */
	Possesses buildPossesses(Agent agent, Capability capability, double score);

	/**
	 * Constructs a {@linkplain Requires}.
	 *
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Requires}.
	 * @param capability
	 *            the {@linkplain Capability} of the {@linkplain Requires}.
	 * @return a {@linkplain Requires}.
	 */
	Requires buildRequires(Role role, Capability capability);

	/**
	 * Constructs an {@linkplain Uses}.
	 *
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Uses}.
	 * @param pmf
	 *            the {@linkplain Pmf} of the {@linkplain Uses}.
	 * @return an {@linkplain Uses}.
	 */
	Uses buildUses(Role role, Pmf pmf);

}
