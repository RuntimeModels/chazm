/*
 * XMLParser.java
 *
 * Created on Dec 18, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.parsers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import model.organization.Organization;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;
import model.organization.id.UniqueIdFactory;

import org.xml.sax.SAXException;

import edu.ksu.cis.agenttool.core.model.Capability;
import edu.ksu.cis.agenttool.core.model.Goal;
import edu.ksu.cis.agenttool.core.model.ModelElement;
import edu.ksu.cis.agenttool.core.model.ModelElement.ModelElementType;
import edu.ksu.cis.agenttool.core.model.Relationship;
import edu.ksu.cis.agenttool.core.model.Relationship.RelationshipType;
import edu.ksu.cis.agenttool.core.model.Role;
import edu.ksu.cis.agenttool.core.model.Schema;
import edu.ksu.cis.agenttool.core.model.Schema.DiagramType;
import edu.ksu.cis.agenttool.core.xml.XMLParseException;

/**
 * The <code>XMLParser</code> for populating an organization model.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public class XmlParser {

	/**
	 * Used only for debugging purposes.
	 */
	private static final boolean DEBUG = false;

	/**
	 * Instantiates an <code>Organization</code> based on the given <code>Path</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code> .
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path) throws SAXException, IOException, ParserConfigurationException {
		return parse(path, new DefaultGoalFactory(), new DefaultIdFactory(), new OrganizationImpl());
	}

	/**
	 * Instantiates an <code>Organization</code> based on the given <code>Path</code> and <code>SpecificationGoalProvider</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to retrieve the <code>SpecificationGoal</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code> and <code>SpecificationGoalProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path, final GoalFactory specificationGoalProvider) throws SAXException, IOException,
			ParserConfigurationException {
		return parse(path, specificationGoalProvider, new DefaultIdFactory(), new OrganizationImpl());
	}

	/**
	 * Instantiates an <code>Organization</code> based on the given <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param uniqueIdFactory
	 *            the <code>UniqueIdentifierProvider</code> that produces <code>UniqueIdentifier</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path, final UniqueIdFactory uniqueIdFactory) throws SAXException, IOException, ParserConfigurationException {
		return parse(path, new DefaultGoalFactory(), uniqueIdFactory, new OrganizationImpl());
	}

	/**
	 * Populates the given <code>Organization</code> based on the given <code>Path</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param organization
	 *            an <code>Organization</code> with which to populate based on the given <code>Path</code>, <code>SpecificationGoalProvider</code>, and
	 *            <code>UniqueIdentifierProvider</code>.
	 * @return the populated <code>Organization</code> based on the given <code>Path</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path, final Organization organization) throws SAXException, IOException, ParserConfigurationException {
		return parse(path, new DefaultGoalFactory(), new DefaultIdFactory(), organization);
	}

	/**
	 * Instantiates an <code>Organization</code> based on the given <code>Path</code>, <code>SpecificationGoalProvider</code>, and
	 * <code>UniqueIdentifierProvider</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to retrieve the <code>SpecificationGoal</code>.
	 * @param uniqueIdFactory
	 *            the <code>UniqueIdentifierProvider</code> that produces <code>UniqueIdentifier</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code> , <code>SpecificationGoalProvider</code>, and
	 *         <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path, final GoalFactory specificationGoalProvider, final UniqueIdFactory uniqueIdFactory) throws SAXException,
			IOException, ParserConfigurationException {
		return parse(path, specificationGoalProvider, uniqueIdFactory, new OrganizationImpl());
	}

	/**
	 * Populates the given <code>Organization</code> based on the given <code>Path</code> and <code>SpecificationGoalProvider</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to retrieve the <code>SpecificationGoal</code>.
	 * @param organization
	 *            the <code>Organization</code> with which to populate based on the given <code>Path</code> and <code>SpecificationGoalProvider</code>.
	 * @return the populated <code>Organization</code> based on the given <code>File</code> and <code>SpecificationGoalProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path, final GoalFactory specificationGoalProvider, final Organization organization) throws SAXException,
			IOException, ParserConfigurationException {
		return parse(path, specificationGoalProvider, new DefaultIdFactory(), organization);
	}

	/**
	 * Populates the given <code>Organization</code> based on the given <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param uniqueIdFactory
	 *            the <code>UniqueIdentifierProvider</code> that produces <code>UniqueIdentifier</code>.
	 * @param organization
	 *            the <code>Organization</code> with which to populate based on the given <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 * @return the populated <code>Organization</code> based on the given <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path, final UniqueIdFactory uniqueIdFactory, final Organization organization) throws SAXException, IOException,
			ParserConfigurationException {
		return parse(path, new DefaultGoalFactory(), uniqueIdFactory, organization);
	}

	/**
	 * Populates the given <code>Organization</code> based on the given <code>Path</code>, <code>SpecificationGoalProvider</code>, and
	 * <code>UniqueIdentifierProvider</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to retrieve the <code>SpecificationGoal</code>.
	 * @param idFactory
	 *            the <code>UniqueIdentifierProvider</code> that produces <code>UniqueIdentifier</code>.
	 * @param organization
	 *            the <code>Organization</code> with which to populate based on the given <code>Path</code>, <code>SpecificationGoalProvider</code>, and
	 *            <code>UniqueIdentifierProvider</code>.
	 * @return the populated <code>Organization</code> based on the given <code>File</code>, <code>SpecificationGoalProvider</code>, and
	 *         <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path, final GoalFactory specificationGoalProvider, final UniqueIdFactory idFactory,
			final Organization organization) throws SAXException, IOException, ParserConfigurationException {
		if (path == null || specificationGoalProvider == null || idFactory == null || organization == null) {
			throw new IllegalArgumentException(String.format(
					"Parameters (file: %s, Specification goal provider: %s, unique identifier provider: %s, organization: %s) cannot be null ", path,
					specificationGoalProvider, idFactory, organization));
		}
		if (Files.exists(path)) {
			if (DEBUG) {
				System.out.println(String.format("Loading file (%s)", path));
			}
			final edu.ksu.cis.agenttool.core.xml.XMLParser parser = new edu.ksu.cis.agenttool.core.xml.XMLParser(path.toString());
			try {
				final Schema schema = parser.getSchema(DiagramType.ROLE);
				/* parse all the goals */
				for (final ModelElement element : schema.getChildren(ModelElementType.GOAL)) {
					final Goal goal = (Goal) element;
					final SpecificationGoal specificationGoal = specificationGoalProvider.buildGoal(idFactory, goal.getName());
					if (specificationGoal == null) {
						throw new IllegalArgumentException(String.format("Cannot find the '%s' specification goal in the given provider", goal.getName()));
					}
					organization.addSpecificationGoal(specificationGoal);
				}
				/* parse all the capabilities */
				for (final ModelElement element : schema.getChildren(ModelElementType.CAPABILITY)) {
					final Capability capability = (Capability) element;
					final UniqueId<model.organization.entity.Capability> capabilityId = idFactory.buildId(model.organization.entity.Capability.class,
							capability.getName());
					final model.organization.entity.Capability c = new CapabilityEntity(capabilityId);
					organization.addCapability(c);
				}
				/* parse all the roles */
				for (final ModelElement element : schema.getChildren(ModelElementType.ROLE)) {
					final Role role = (Role) element;
					/* only roles that are not inherited are added */
					if (role.getDestRelationships(RelationshipType.INHERITS).size() == 0) {
						final UniqueId<model.organization.entity.Role> roleId = idFactory.buildId(model.organization.entity.Role.class, role.getName());
						final model.organization.entity.Role r = new RoleEntity(roleId);
						organization.addRole(r);
						/* set up the achieves relation */
						for (final Relationship achieves : role.getSrcRelationships(RelationshipType.ACHIEVES)) {
							final Goal goal = (Goal) achieves.getChild();
							final UniqueId<SpecificationGoal> goalId = idFactory.buildId(SpecificationGoal.class, goal.getName());
							organization.addAchieves(roleId, goalId);
						}
						/* set up the requires relation */
						for (final Relationship requires : role.getSrcRelationships(RelationshipType.REQUIRES)) {
							final Capability capability = (Capability) requires.getChild();
							final UniqueId<model.organization.entity.Capability> capabilityId = idFactory.buildId(model.organization.entity.Capability.class,
									capability.getName());
							organization.addRequires(roleId, capabilityId);
						}
						/* set up requires relation from inheritance */
						for (final List<Relationship> inherits = role.getSrcRelationships(RelationshipType.INHERITS); !inherits.isEmpty();) {
							final Role parent = (Role) inherits.remove(0).getChild();
							for (final Relationship requires : parent.getSrcRelationships(RelationshipType.REQUIRES)) {
								final Capability capability = (Capability) requires.getChild();
								final UniqueId<model.organization.entity.Capability> capabilityId = idFactory.buildId(
										model.organization.entity.Capability.class, capability.getName());
								organization.addRequires(roleId, capabilityId);
							}
							inherits.addAll(parent.getSrcRelationships(RelationshipType.INHERITS));
						}
					}
				}
			} catch (final XMLParseException e) {
				/*
				 * replaces custom agent tool parse exception with java's parse exception
				 */
				throw new ParserConfigurationException(e.getMessage());
			}
		} else {
			throw new FileNotFoundException(String.format("File (%s) does not exists!", path.toString()));
		}
		return organization;
	}

}
