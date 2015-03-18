package model.organization.parsers;

import static model.organization.validation.Checks.checkNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import model.organization.Organization;
import model.organization.entity.Capability;
import model.organization.entity.EntityFactory;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.id.UniqueId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import edu.ksu.cis.agenttool.core.model.Goal;
import edu.ksu.cis.agenttool.core.model.ModelElement;
import edu.ksu.cis.agenttool.core.model.ModelElement.ModelElementType;
import edu.ksu.cis.agenttool.core.model.Relationship;
import edu.ksu.cis.agenttool.core.model.Relationship.RelationshipType;
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

	private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

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
	// public static Organization parse(final Path path) throws SAXException, IOException, ParserConfigurationException {
	// return parse(path, new DefaultGoalFactory(), new DefaultIdFactory(), new OrganizationImpl());
	// }

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
	// public static Organization parse(final Path path, final GoalFactory specificationGoalProvider) throws SAXException, IOException,
	// ParserConfigurationException {
	// return parse(path, specificationGoalProvider, new DefaultIdFactory(), new OrganizationImpl());
	// }

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
	// public static Organization parse(final Path path, final UniqueIdFactory uniqueIdFactory) throws SAXException, IOException, ParserConfigurationException {
	// return parse(path, new DefaultGoalFactory(), uniqueIdFactory, new OrganizationImpl());
	// }

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
	// public static Organization parse(final Path path, final Organization organization) throws SAXException, IOException, ParserConfigurationException {
	// return parse(path, new DefaultGoalFactory(), new DefaultIdFactory(), organization);
	// }

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
	// public static Organization parse(final Path path, final GoalFactory specificationGoalProvider, final UniqueIdFactory uniqueIdFactory) throws
	// SAXException,
	// IOException, ParserConfigurationException {
	// return parse(path, specificationGoalProvider, uniqueIdFactory, new OrganizationImpl());
	// }

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
	// public static Organization parse(final Path path, final GoalFactory specificationGoalProvider, final Organization organization) throws SAXException,
	// IOException, ParserConfigurationException {
	// return parse(path, specificationGoalProvider, new DefaultIdFactory(), organization);
	// }

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
	// public static Organization parse(final Path path, final UniqueIdFactory uniqueIdFactory, final Organization organization) throws SAXException,
	// IOException,
	// ParserConfigurationException {
	// return parse(path, new DefaultGoalFactory(), uniqueIdFactory, organization);
	// }

	/**
	 * Populates the given <code>Organization</code> based on the given <code>Path</code>, <code>SpecificationGoalProvider</code>, and
	 * <code>UniqueIdentifierProvider</code>.
	 *
	 * @param path
	 *            the <code>Path</code> which contains the necessary information for construction an <code>Organization</code>.
	 * @param entityFactory
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
	public static Organization parse(final Path path, final EntityFactory entityFactory, final IdFactory idFactory, final Organization organization)
			throws SAXException, IOException, ParserConfigurationException {
		checkNotNull(path, "path");
		checkNotNull(entityFactory, "entityFactory");
		checkNotNull(idFactory, "idFactory");
		checkNotNull(organization, "organization");
		if (Files.exists(path)) {
			logger.debug("Loading file ({})", path);
			final edu.ksu.cis.agenttool.core.xml.XMLParser parser = new edu.ksu.cis.agenttool.core.xml.XMLParser(path.toString());
			try {
				final Schema schema = parser.getSchema(DiagramType.ROLE);
				/* parse all the goals */
				for (final ModelElement element : schema.getChildren(ModelElementType.GOAL)) {
					final Goal goal = (Goal) element;
					final SpecificationGoal specificationGoal = entityFactory
							.buildSpecificationGoal(idFactory.buildId(SpecificationGoal.class, goal.getName()));
					if (specificationGoal == null) {
						throw new IllegalArgumentException(String.format("Cannot find the '%s' specification goal in the given provider", goal.getName()));
					}
					organization.addSpecificationGoal(specificationGoal);
				}
				/* parse all the capabilities */
				for (final ModelElement element : schema.getChildren(ModelElementType.CAPABILITY)) {
					final edu.ksu.cis.agenttool.core.model.Capability capability = (edu.ksu.cis.agenttool.core.model.Capability) element;
					final UniqueId<Capability> id = idFactory.buildId(Capability.class, capability.getName());
					final Capability c = entityFactory.buildCapability(id);
					organization.addCapability(c);
				}
				/* parse all the roles */
				for (final ModelElement element : schema.getChildren(ModelElementType.ROLE)) {
					final edu.ksu.cis.agenttool.core.model.Role role = (edu.ksu.cis.agenttool.core.model.Role) element;
					/* only roles that are not inherited are added */
					if (role.getDestRelationships(RelationshipType.INHERITS).size() == 0) {
						final UniqueId<Role> roleId = idFactory.buildId(Role.class, role.getName());
						final Role r = entityFactory.buildRole(roleId);
						organization.addRole(r);
						/* set up the achieves relation */
						for (final Relationship achieves : role.getSrcRelationships(RelationshipType.ACHIEVES)) {
							final Goal goal = (Goal) achieves.getChild();
							final UniqueId<SpecificationGoal> goalId = idFactory.buildId(SpecificationGoal.class, goal.getName());
							organization.addAchieves(roleId, goalId);
						}
						/* set up the requires relation */
						for (final Relationship requires : role.getSrcRelationships(RelationshipType.REQUIRES)) {
							final edu.ksu.cis.agenttool.core.model.Capability capability = (edu.ksu.cis.agenttool.core.model.Capability) requires.getChild();
							final UniqueId<Capability> capabilityId = idFactory.buildId(Capability.class, capability.getName());
							organization.addRequires(roleId, capabilityId);
						}
						/* set up requires relation from inheritance */
						for (final List<Relationship> inherits = role.getSrcRelationships(RelationshipType.INHERITS); !inherits.isEmpty();) {
							final edu.ksu.cis.agenttool.core.model.Role parent = (edu.ksu.cis.agenttool.core.model.Role) inherits.remove(0).getChild();
							for (final Relationship requires : parent.getSrcRelationships(RelationshipType.REQUIRES)) {
								final edu.ksu.cis.agenttool.core.model.Capability capability = (edu.ksu.cis.agenttool.core.model.Capability) requires
										.getChild();
								final UniqueId<Capability> capabilityId = idFactory.buildId(Capability.class, capability.getName());
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
			logger.info("File ({}) does not exists", path.toString());
			throw new FileNotFoundException(String.format("File (%s) does not exists!", path.toString()));
		}
		return organization;
	}

}
