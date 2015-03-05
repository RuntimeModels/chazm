/*
 * ModelFactory.java
 *
 * Aug 29, 2005
 *
 * See License.txt file the license agreement.
 */
package org.models.test.organization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.models.organization.entity.Agent;
import org.models.organization.entity.AgentImpl;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.AttributeImpl;
import org.models.organization.entity.Capability;
import org.models.organization.entity.CapabilityImpl;
import org.models.organization.entity.Organization;
import org.models.organization.entity.OrganizationImpl;
import org.models.organization.entity.Role;
import org.models.organization.entity.RoleImpl;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.entity.SpecificationGoalImpl;
import org.models.organization.entity.Attribute.Type;
import org.models.organization.identifier.StringIdentifier;

/**
 * The <code>ModelFactory</code> class allows the random generation of a
 * organization model based on the current implementation available from the
 * package <code>model.organization.function</code>.
 * 
 * @author Christopher Zhong
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class ModelFactory {

	/**
	 * <code>DEBUG</code> is used only for debugging purposes.
	 */
	private static final boolean DEBUG = true;

	/**
	 * Create an organization that is achievable given the following inputs:
	 * <ul>
	 * <li>number of goals</li>
	 * <li>number of roles</li>
	 * <li>number of agents</li>
	 * <li>average number of roles per goal</li>
	 * <li>average number of agents per role</li>
	 * </ul>
	 * 
	 * @param numberOfGoals
	 *            the number of goals to create.
	 * @param numberOfRoles
	 *            the number of roles to create.
	 * @param numberOfAgents
	 *            the number of agents to create.
	 * @param averageNumberOfRolesPerGoal
	 *            the average number of roles per goal.
	 * @param averageNumberOfAgentsPerRole
	 *            the average number of agents per role.
	 * @return an organization that is achievable.
	 */
	public static Organization generate(final int numberOfGoals,
			final int numberOfRoles, final int numberOfAgents,
			final int averageNumberOfRolesPerGoal,
			final int averageNumberOfAgentsPerRole) {
		assert averageNumberOfRolesPerGoal <= numberOfRoles
				&& averageNumberOfAgentsPerRole <= numberOfAgents;
		/* create the organization */
		final Organization organization = new OrganizationImpl();
		/* create the specified number of goals */
		final List<SpecificationGoal> goals = new ArrayList<>(numberOfGoals);
		for (int i = 0; i < numberOfGoals; i++) {
			final String goalName = String.format("Goal %s", i + 1);
			final StringIdentifier goalIdentifier = StringIdentifier
					.getIdentifier(goalName);
			final SpecificationGoal goal = new SpecificationGoalImpl(
					goalIdentifier);
			goals.add(goal);
			organization.addSpecificationGoal(goal);
		}
		/* create the specified number of roles */
		final List<Role> roles = new ArrayList<>(numberOfRoles);
		for (int i = 0; i < numberOfRoles; i++) {
			final String roleName = String.format("Role %s", i + 1);
			final StringIdentifier roleIdentifier = StringIdentifier
					.getIdentifier(roleName);
			final Role role = new RoleImpl(roleIdentifier);
			roles.add(role);
			organization.addRole(role);
			/* create the capability for the role */
			final String capabilityName = String.format("Capability %s", i + 1);
			final StringIdentifier capabilityIdentifier = StringIdentifier
					.getIdentifier(capabilityName);
			final Capability capability = new CapabilityImpl(
					capabilityIdentifier);
			role.addRequires(capability);
			organization.addCapability(capability);
			/* create the attribute for the role */
			final String attributeName = String.format("Attribute %s", i + 1);
			final StringIdentifier attributeIdentifier = StringIdentifier
					.getIdentifier(attributeName);
			final Type[] attributeTypes = Type.values();
			final int index = (int) (Math.random() * attributeTypes.length);
			final Attribute attribute = new AttributeImpl(attributeIdentifier,
					attributeTypes[index]);
			role.addNeeds(attribute);
			organization.addAttribute(attribute);
		}
		/* create the specified number of agents */
		final List<Agent<?>> agents = new ArrayList<>(numberOfAgents);
		for (int i = 0; i < numberOfAgents; i++) {
			final String agentName = String.format("Agent %s", i + 1);
			final StringIdentifier agentIdentifier = StringIdentifier
					.getIdentifier(agentName);
			final Agent<Object> agent = new AgentImpl<>(agentIdentifier);
			agents.add(agent);
			organization.addAgent(agent);
		}
		/* create the role-goal relationships */
		if (DEBUG) {
			System.out
					.println("--- Creating Achieves Relation (Goal-Role) ---");
		}
		/* randomizes the goals list */
		Collections.shuffle(goals);
		/* the total number of roles that has been assigned to goals so far */
		int totalRolesAssigned = 0;
		/* the numbers of goals that still needs to be assigned */
		int numberOfGoalsLeft = goals.size();
		/* every goal will be assigned at least one role */
		for (final SpecificationGoal goal : goals) {
			if (DEBUG) {
				System.out.println(String.format("Goal: %s ", goal));
			}
			/*
			 * compute range min .. max of roles needed to be assigned to the
			 * current goal to maintain the average role per goal
			 * 
			 * average = (roles assigned + (goals left - 1)) / number of goals
			 */
			final int min = Math.max(1, averageNumberOfRolesPerGoal
					* numberOfGoals - totalRolesAssigned
					- (numberOfGoalsLeft - 1) * numberOfRoles);
			final int max = Math.min(numberOfRoles, averageNumberOfRolesPerGoal
					* numberOfGoals - totalRolesAssigned
					- (numberOfGoalsLeft - 1));
			if (DEBUG) {
				System.out.println(String
						.format("  Range: %s ... %s", min, max));
			}
			/*
			 * once the range (min .. max) is computed, pick a number randomly
			 * from that range as the number of roles that will be assigned to
			 * the current goal
			 */
			final int rolesToAssign = (int) Math.round(Math.random()
					* (max - min) + min);
			totalRolesAssigned += rolesToAssign;
			numberOfGoalsLeft--;
			if (DEBUG) {
				System.out.println(String.format(
						"    # Roles to Assign = %s (Total Assigned: %s)",
						rolesToAssign, totalRolesAssigned));
				System.out.println(String.format("    Goals Left = %s",
						numberOfGoalsLeft));
			}
			/*
			 * once we have the number of roles to be assigned to the current
			 * goal, we can go ahead and create the achieves relationship
			 */
			if (DEBUG) {
				System.out.println("  Creating Achieves Relation:");
			}
			/* randomizes the roles list */
			Collections.shuffle(roles);
			for (int i = 0; i < rolesToAssign; i++) {
				final Role role = roles.get(i);
				role.addAchieves(goal);
				if (DEBUG) {
					System.out.println(String.format("    %s <-> %s", role,
							goal));
				}
			}
		}

		/* create the agent-role relationships */
		if (DEBUG) {
			System.out
					.println("--- Creating Capable Relation (Role-Agent) ---");
		}
		/* randomizes the roles list */
		Collections.shuffle(roles);
		/* the total number of agents that has been assigned to roles so far */
		int totalAgentsAssigned = 0;
		/* the numbers of roles that still needs to be assigned */
		int numberOfRolesLeft = roles.size();
		/* every role will be assigned at least one agent */
		for (final Role role : roles) {
			if (DEBUG) {
				System.out.println(String.format("Role: %s ", role));
			}
			/*
			 * compute range min .. max of agents needed to be assigned to the
			 * current role to maintain the average agent per role
			 * 
			 * average = (agents assigned + (roles left - 1)) / number of roles
			 */
			final int min = Math.max(1, averageNumberOfAgentsPerRole
					* numberOfRoles - totalAgentsAssigned
					- (numberOfRolesLeft - 1) * numberOfAgents);
			final int max = Math.min(numberOfAgents,
					averageNumberOfAgentsPerRole * numberOfRoles
							- totalAgentsAssigned - (numberOfRolesLeft - 1));
			if (DEBUG) {
				System.out.println(String
						.format("  Range: %s ... %s", min, max));
			}
			/*
			 * once the range (min .. max) is computed, pick a number randomly
			 * from that range as the number of agents that will be assigned to
			 * the current role
			 */
			final int agentsToAssign = (int) Math.round(Math.random()
					* (max - min) + min);
			totalAgentsAssigned += agentsToAssign;
			numberOfRolesLeft--;
			if (DEBUG) {
				System.out.println(String.format(
						"    Agents to Assign = %s (Total Assigned: %s)",
						agentsToAssign, totalAgentsAssigned));
				System.out.println(String.format("    Roles Left = %s",
						numberOfRolesLeft));
			}
			/*
			 * once we have the number of agents to be assigned to the current
			 * role, we can go ahead and create the possesses relationship
			 */
			if (DEBUG) {
				System.out.println("  Creating Possesses & Has Relation:");
			}
			/* randomizes the agents list */
			Collections.shuffle(agents);
			for (int i = 0; i < agentsToAssign; i++) {
				final Agent<?> agent = agents.get(i);
				for (final Capability capability : role.getRequiresSet()) {
					/*
					 * randomly generates a possesses score, where 0.0 < score
					 * <= 1.0
					 */
					double score = Math.random();
					if (Double.compare(score, 0.0) == 0) {
						score = 1.0;
					}
					agent.addPossesses(capability, score);
					if (DEBUG) {
						System.out.println(String.format("    %s <-> %s : %s",
								agent, capability, score));
					}
				}
				for (final Attribute attribute : role.getNeedsSet()) {
					double value = Math.random();
					switch (attribute.getType()) {
					case NEGATIVE_QUANTITY:
					case POSITIVE_QUANTITY:
						value *= Integer.MAX_VALUE;
						break;
					case NEGATIVE_UNBOUNDED:
					case POSITIVE_UNBOUNDED:
						value *= Integer.MAX_VALUE;
						if (Math.random() < 0.5) {
							value *= -1;
						}
						break;
					case NEGATIVE_QUALITY:
					case POSITIVE_QUALITY:
						break;
					default:
						throw new IllegalArgumentException(String.format(
								"Unknown Enum (%s)", attribute.getType()));
					}
					agent.addHas(attribute, value);
					if (DEBUG) {
						System.out.println(String.format("    %s <-> %s : %s",
								agent, attribute, value));
					}
				}
			}
		}
		return organization;
	}

}
