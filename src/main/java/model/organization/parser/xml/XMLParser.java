/*
 * XMLParser.java
 *
 * Created on Dec 18, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.parser.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import model.organization.entity.CapabilityImpl;
import model.organization.entity.Organization;
import model.organization.entity.OrganizationImpl;
import model.organization.entity.RoleImpl;
import model.organization.entity.SpecificationGoal;
import model.organization.identifier.UniqueIdentifier;
import model.organization.parser.DefaultSpecificationGoalProvider;
import model.organization.parser.DefaultUniqueIdentifierProvider;
import model.organization.parser.SpecificationGoalProvider;
import model.organization.parser.UniqueIdentifierProvider;

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
 * @version $Revision: 1.1.2.2 $, $Date: 2012/05/02 00:09:15 $
 * @since 4.0
 */
public class XMLParser {

	/**
	 * Used only for debugging purposes.
	 */
	private static final boolean DEBUG = false;

	/**
	 * Instantiates an <code>Organization</code> based on the given
	 * <code>Path</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code>
	 *         .
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path) throws SAXException,
			IOException, ParserConfigurationException {
		return parse(path, new DefaultSpecificationGoalProvider(),
				new DefaultUniqueIdentifierProvider(), new OrganizationImpl());
	}

	/**
	 * Instantiates an <code>Organization</code> based on the given
	 * <code>Path</code> and <code>SpecificationGoalProvider</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to
	 *            retrieve the <code>SpecificationGoal</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code>
	 *         and <code>SpecificationGoalProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path,
			final SpecificationGoalProvider specificationGoalProvider)
			throws SAXException, IOException, ParserConfigurationException {
		return parse(path, specificationGoalProvider,
				new DefaultUniqueIdentifierProvider(), new OrganizationImpl());
	}

	/**
	 * Instantiates an <code>Organization</code> based on the given
	 * <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> that produces
	 *            <code>UniqueIdentifier</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code>
	 *         and <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path,
			final UniqueIdentifierProvider uniqueIdentifierProvider)
			throws SAXException, IOException, ParserConfigurationException {
		return parse(path, new DefaultSpecificationGoalProvider(),
				uniqueIdentifierProvider, new OrganizationImpl());
	}

	/**
	 * Populates the given <code>Organization</code> based on the given
	 * <code>Path</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @param organization
	 *            an <code>Organization</code> with which to populate based on
	 *            the given <code>Path</code>,
	 *            <code>SpecificationGoalProvider</code>, and
	 *            <code>UniqueIdentifierProvider</code>.
	 * @return the populated <code>Organization</code> based on the given
	 *         <code>Path</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path,
			final Organization organization) throws SAXException, IOException,
			ParserConfigurationException {
		return parse(path, new DefaultSpecificationGoalProvider(),
				new DefaultUniqueIdentifierProvider(), organization);
	}

	/**
	 * Instantiates an <code>Organization</code> based on the given
	 * <code>Path</code>, <code>SpecificationGoalProvider</code>, and
	 * <code>UniqueIdentifierProvider</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to
	 *            retrieve the <code>SpecificationGoal</code>.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> that produces
	 *            <code>UniqueIdentifier</code>.
	 * @return an <code>Organization</code> based on the given <code>Path</code>
	 *         , <code>SpecificationGoalProvider</code>, and
	 *         <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path,
			final SpecificationGoalProvider specificationGoalProvider,
			final UniqueIdentifierProvider uniqueIdentifierProvider)
			throws SAXException, IOException, ParserConfigurationException {
		return parse(path, specificationGoalProvider, uniqueIdentifierProvider,
				new OrganizationImpl());
	}

	/**
	 * Populates the given <code>Organization</code> based on the given
	 * <code>Path</code> and <code>SpecificationGoalProvider</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to
	 *            retrieve the <code>SpecificationGoal</code>.
	 * @param organization
	 *            the <code>Organization</code> with which to populate based on
	 *            the given <code>Path</code> and
	 *            <code>SpecificationGoalProvider</code>.
	 * @return the populated <code>Organization</code> based on the given
	 *         <code>File</code> and <code>SpecificationGoalProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path,
			final SpecificationGoalProvider specificationGoalProvider,
			final Organization organization) throws SAXException, IOException,
			ParserConfigurationException {
		return parse(path, specificationGoalProvider,
				new DefaultUniqueIdentifierProvider(), organization);
	}

	/**
	 * Populates the given <code>Organization</code> based on the given
	 * <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> that produces
	 *            <code>UniqueIdentifier</code>.
	 * @param organization
	 *            the <code>Organization</code> with which to populate based on
	 *            the given <code>Path</code> and
	 *            <code>UniqueIdentifierProvider</code>.
	 * @return the populated <code>Organization</code> based on the given
	 *         <code>Path</code> and <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path,
			final UniqueIdentifierProvider uniqueIdentifierProvider,
			final Organization organization) throws SAXException, IOException,
			ParserConfigurationException {
		return parse(path, new DefaultSpecificationGoalProvider(),
				uniqueIdentifierProvider, organization);
	}

	/**
	 * Populates the given <code>Organization</code> based on the given
	 * <code>Path</code>, <code>SpecificationGoalProvider</code>, and
	 * <code>UniqueIdentifierProvider</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> which contains the necessary information
	 *            for construction an <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> in which to
	 *            retrieve the <code>SpecificationGoal</code>.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> that produces
	 *            <code>UniqueIdentifier</code>.
	 * @param organization
	 *            the <code>Organization</code> with which to populate based on
	 *            the given <code>Path</code>,
	 *            <code>SpecificationGoalProvider</code>, and
	 *            <code>UniqueIdentifierProvider</code>.
	 * @return the populated <code>Organization</code> based on the given
	 *         <code>File</code>, <code>SpecificationGoalProvider</code>, and
	 *         <code>UniqueIdentifierProvider</code>.
	 * @throws ParserConfigurationException
	 *             if a <code>DocumentBuilder</code> cannot be created which
	 *             satisfies the configuration requested.
	 * @throws IOException
	 *             if any IO errors occur.
	 * @throws SAXException
	 *             if any parse errors occur.
	 */
	public static Organization parse(final Path path,
			final SpecificationGoalProvider specificationGoalProvider,
			final UniqueIdentifierProvider uniqueIdentifierProvider,
			final Organization organization) throws SAXException, IOException,
			ParserConfigurationException {
		if (path == null || specificationGoalProvider == null
				|| uniqueIdentifierProvider == null || organization == null) {
			throw new IllegalArgumentException(
					String.format(
							"Parameters (file: %s, Specification goal provider: %s, unique identifier provider: %s, organization: %s) cannot be null ",
							path, specificationGoalProvider,
							uniqueIdentifierProvider, organization));
		}
		if (Files.exists(path)) {
			if (DEBUG) {
				System.out.println(String.format("Loading file (%s)", path));
			}
			final edu.ksu.cis.agenttool.core.xml.XMLParser parser = new edu.ksu.cis.agenttool.core.xml.XMLParser(
					path.toString());
			try {
				final Schema schema = parser.getSchema(DiagramType.ROLE);
				/* parse all the goals */
				for (final ModelElement element : schema
						.getChildren(ModelElementType.GOAL)) {
					final Goal goal = (Goal) element;
					final SpecificationGoal specificationGoal = specificationGoalProvider
							.getSpecificationGoal(goal.getName(),
									uniqueIdentifierProvider);
					if (specificationGoal == null) {
						throw new IllegalArgumentException(
								String.format(
										"Cannot find the '%s' specification goal in the given provider",
										goal.getName()));
					}
					organization.addSpecificationGoal(specificationGoal);
				}
				/* parse all the capabilities */
				for (final ModelElement element : schema
						.getChildren(ModelElementType.CAPABILITY)) {
					final Capability capability = (Capability) element;
					final UniqueIdentifier capabilityIdentifier = uniqueIdentifierProvider
							.getUniqueIdentifier(capability.getName(),
									model.organization.entity.Capability.class);
					final model.organization.entity.Capability c = new CapabilityImpl(
							capabilityIdentifier);
					organization.addCapability(c);
				}
				/* parse all the roles */
				for (final ModelElement element : schema
						.getChildren(ModelElementType.ROLE)) {
					final Role role = (Role) element;
					/* only roles that are not inherited are added */
					if (role.getDestRelationships(RelationshipType.INHERITS)
							.size() == 0) {
						final UniqueIdentifier roleIdentifier = uniqueIdentifierProvider
								.getUniqueIdentifier(role.getName(),
										model.organization.entity.Role.class);
						final model.organization.entity.Role r = new RoleImpl(
								roleIdentifier);
						organization.addRole(r);
						/* set up the achieves relation */
						for (final Relationship achieves : role
								.getSrcRelationships(RelationshipType.ACHIEVES)) {
							final Goal goal = (Goal) achieves.getChild();
							final UniqueIdentifier goalIdentifier = uniqueIdentifierProvider
									.getUniqueIdentifier(goal.getName(),
											SpecificationGoal.class);
							organization.specifyAchievesRelation(
									roleIdentifier, goalIdentifier);
						}
						/* set up the requires relation */
						for (final Relationship requires : role
								.getSrcRelationships(RelationshipType.REQUIRES)) {
							final Capability capability = (Capability) requires
									.getChild();
							final UniqueIdentifier capabilityIdentifier = uniqueIdentifierProvider
									.getUniqueIdentifier(
											capability.getName(),
											model.organization.entity.Capability.class);
							organization.specifyRequiresRelation(
									roleIdentifier, capabilityIdentifier);
						}
						/* set up requires relation from inheritance */
						for (final List<Relationship> inherits = role
								.getSrcRelationships(RelationshipType.INHERITS); !inherits
								.isEmpty();) {
							final Role parent = (Role) inherits.remove(0)
									.getChild();
							for (final Relationship requires : parent
									.getSrcRelationships(RelationshipType.REQUIRES)) {
								final Capability capability = (Capability) requires
										.getChild();
								final UniqueIdentifier capabilityIdentifier = uniqueIdentifierProvider
										.getUniqueIdentifier(
												capability.getName(),
												model.organization.entity.Capability.class);
								organization.specifyRequiresRelation(
										roleIdentifier, capabilityIdentifier);
							}
							inherits.addAll(parent
									.getSrcRelationships(RelationshipType.INHERITS));
						}
					}
				}
			} catch (final XMLParseException e) {
				/*
				 * replaces custom agent tool parse exception with java's parse
				 * exception
				 */
				throw new ParserConfigurationException(e.getMessage());
			}
		} else {
			throw new FileNotFoundException(String.format(
					"File (%s) does not exists!", path.toString()));
		}
		return organization;
	}

}
