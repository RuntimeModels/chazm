package model.organization.parsers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import message.E;
import message.L;
import model.organization.Organization;
import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Capability;
import model.organization.entity.Characteristic;
import model.organization.entity.EntityFactory;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Pmf;
import model.organization.entity.Policy;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.id.UniqueId;
import model.organization.relation.Assignment;
import model.organization.relation.AssignmentFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@linkplain XmlParser} class is able to parse XML files and populate an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
@Singleton
public class XmlParser {

	@FunctionalInterface
	private static interface RunLater {
		void run() throws XMLStreamException;
	}

	private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);
	private static final String ROLE_DIAGRAM_ELEMENT = "RoleDiagram"; //$NON-NLS-1$
	private static final String AGENT_ELEMENT = "Agent"; //$NON-NLS-1$
	private static final String ATTRIBUTE_ELEMENT = "Attribute"; //$NON-NLS-1$
	private static final String CAPABILITY_ELEMENT = "Capability"; //$NON-NLS-1$
	private static final String CHARACTERISTIC_ELEMENT = "Characteristic"; //$NON-NLS-1$
	private static final String INSTANCEGOAL_ELEMENT = "InstanceGoal"; //$NON-NLS-1$
	private static final String PMF_ELEMENT = "Pmf"; //$NON-NLS-1$
	private static final String POLICY_ELEMENT = "Policy"; //$NON-NLS-1$
	private static final String ROLE_ELEMENT = "Role"; //$NON-NLS-1$
	private static final String GOAL_ELEMENT = "Goal"; //$NON-NLS-1$
	private static final String ACHIEVES_ELEMENT = "achieves"; //$NON-NLS-1$
	private static final String ASSIGNMENT_ELEMENT = "assignment"; //$NON-NLS-1$
	private static final String CONTAINS_ELEMENT = "contains"; //$NON-NLS-1$
	private static final String HAS_ELEMENT = "has"; //$NON-NLS-1$
	private static final String MODERATES_ELEMENT = "moderates"; //$NON-NLS-1$
	private static final String NEEDS_ELEMENT = "needs"; //$NON-NLS-1$
	private static final String POSSESSES_ELEMENT = "possesses"; //$NON-NLS-1$
	private static final String REQUIRES_ELEMENT = "requires"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$
	private static final String SPECIFICATION_ATTRIBUTE = "specification"; //$NON-NLS-1$
	private static final String VALUE_ATTRIBUTE = "value"; //$NON-NLS-1$
	private static final String SCORE_ATTRIBUTE = "score"; //$NON-NLS-1$
	private static final String AGENT_ATTRIBUTE = "agent"; //$NON-NLS-1$
	private static final String ROLE_ATTRIBUTE = "role"; //$NON-NLS-1$
	private static final String GOAL_ATTRIBUTE = "goal"; //$NON-NLS-1$
	private static final String CHILD_ELEMENT = "child"; //$NON-NLS-1$

	private final IdFactory idFactory;
	private final EntityFactory entityFactory;
	private final AssignmentFactory assignmentFactory;

	@Inject
	XmlParser(@NotNull final IdFactory idFactory, @NotNull final EntityFactory entityFactory, @NotNull final AssignmentFactory assignmentFactory) {
		this.idFactory = idFactory;
		this.entityFactory = entityFactory;
		this.assignmentFactory = assignmentFactory;
	}

	/**
	 * Parses an XML file and populates an {@linkplain Organization}.
	 *
	 * @param organization
	 *            the {@linkplain Organization}.
	 * @param inputStream
	 *            the {@linkplain InputStream}.
	 * @throws XMLStreamException
	 */
	public void parse(@NotNull final Organization organization, @NotNull final InputStream inputStream) throws XMLStreamException {
		final XMLInputFactory factory = XMLInputFactory.newInstance();
		final XMLEventReader reader = factory.createXMLEventReader(inputStream);
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
				final StartElement element = event.asStartElement();
				logger.debug(L.START_TAG.get(), element);
				final QName name = element.getName();
				if (ROLE_DIAGRAM_ELEMENT.equals(name.getLocalPart())) {
					parseDiagram(organization, reader, name);
				}
			}
		}
	}

	private void parseDiagram(final Organization organization, final XMLEventReader reader, final QName tagName) throws XMLStreamException {
		final Map<String, UniqueId<Agent>> agents = new HashMap<>();
		final Map<String, UniqueId<Attribute>> attributes = new HashMap<>();
		final Map<String, UniqueId<Capability>> capabilities = new HashMap<>();
		final Map<String, UniqueId<Characteristic>> characteristics = new HashMap<>();
		final Map<String, UniqueId<InstanceGoal>> instanceGoals = new HashMap<>();
		final Map<String, UniqueId<Pmf>> pmfs = new HashMap<>();
		final Map<String, UniqueId<Policy>> policies = new HashMap<>();
		final Map<String, UniqueId<Role>> roles = new HashMap<>();
		final Map<String, UniqueId<SpecificationGoal>> specificationGoals = new HashMap<>();
		final List<RunLater> list1 = new ArrayList<>();
		final List<RunLater> list2 = new ArrayList<>();
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final QName name = element.getName();
				if (AGENT_ELEMENT.equals(name.getLocalPart())) {
					// TODO parse contact info
					final Agent.ContactInfo contactInfo = new Agent.ContactInfo() {};
					final UniqueId<Agent> id = build(Agent.class, agents, element, f -> entityFactory.buildAgent(f, contactInfo), organization::addAgent);
					parseAgent(organization, reader, name, id, attributes, capabilities, list1);
				} else if (ASSIGNMENT_ELEMENT.equals(name.getLocalPart())) {
					parseAssignment(organization, element, agents, instanceGoals, roles, list2);
				} else if (ATTRIBUTE_ELEMENT.equals(name.getLocalPart())) {
					try {
						final Attribute.Type type = Attribute.Type.valueOf(getAttributeValue(element, TYPE_ATTRIBUTE));
						build(Attribute.class, attributes, element, f -> entityFactory.buildAttribute(f, type), organization::addAttribute);
					} catch (final IllegalArgumentException e) {
						throw new XMLStreamException(e);
					}
				} else if (CAPABILITY_ELEMENT.equals(name.getLocalPart())) {
					build(Capability.class, capabilities, element, f -> entityFactory.buildCapability(f), organization::addCapability);
				} else if (CHARACTERISTIC_ELEMENT.equals(name.getLocalPart())) {
					build(Characteristic.class, characteristics, element, f -> entityFactory.buildCharacteristic(f), organization::addCharacteristic);
				} else if (INSTANCEGOAL_ELEMENT.equals(name.getLocalPart())) {
					parseInstanceGoal(organization, element, specificationGoals, instanceGoals, list1);
				} else if (PMF_ELEMENT.equals(name.getLocalPart())) {
					final UniqueId<Pmf> id = build(Pmf.class, pmfs, element, f -> entityFactory.buildPmf(f), organization::addPmf);
					parsePmf(organization, reader, name, id, attributes, list1);
				} else if (POLICY_ELEMENT.equals(name.getLocalPart())) {
					build(Policy.class, policies, element, f -> entityFactory.buildPolicy(f), organization::addPolicy);
				} else if (ROLE_ELEMENT.equals(name.getLocalPart())) {
					final UniqueId<Role> id = build(Role.class, roles, element, f -> entityFactory.buildRole(f), organization::addRole);
					parseRole(organization, reader, name, id, attributes, capabilities, characteristics, specificationGoals, list1);
				} else if (GOAL_ELEMENT.equals(name.getLocalPart())) {
					build(SpecificationGoal.class, specificationGoals, element, f -> entityFactory.buildSpecificationGoal(f),
							organization::addSpecificationGoal);
				}
			} else if (event.isEndElement()) {
				final EndElement element = event.asEndElement();
				if (element.getName().equals(tagName)) {
					for (final RunLater r : list1) {
						r.run();
					}
					for (final RunLater r : list2) {
						r.run();
					}
					return;
				}
			}
		}
		throw new XMLStreamException(E.MISSING_END_TAG.get(tagName)); // should not happen as XMLEventReader will do it for us
	}

	private <T> UniqueId<T> build(final Class<T> clazz, final Map<String, UniqueId<T>> map, final StartElement element, final Function<UniqueId<T>, T> f,
			final Consumer<T> c) throws XMLStreamException {
		final UniqueId<T> id = idFactory.build(clazz, getAttributeValue(element, NAME_ATTRIBUTE));
		map.put(getAttributeValue(element, ID_ATTRIBUTE), id);
		final T t = f.apply(id);
		try {
			c.accept(t);
		} catch (final IllegalArgumentException e) {
			throw new XMLStreamException(e);
		}
		return id;
	}

	private String getAttributeValue(final StartElement element, final String localPart) throws XMLStreamException {
		final javax.xml.stream.events.Attribute attribute = element.getAttributeByName(new QName(localPart));
		if (attribute == null) {
			throw new XMLStreamException(E.MISSING_ATTRIBUTE_IN_TAG.get(element.getName().getLocalPart(), localPart));
		}
		return attribute.getValue();
	}

	private void parseAgent(final Organization organization, final XMLEventReader reader, final QName tagName, final UniqueId<Agent> id,
			final Map<String, UniqueId<Attribute>> attributes, final Map<String, UniqueId<Capability>> capabilities, final List<RunLater> list)
			throws XMLStreamException {
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final QName name = element.getName();
				if (HAS_ELEMENT.equals(name.getLocalPart())) {
					final List<String> ids = collectChild(reader, name);
					try {
						final double value = Double.valueOf(getAttributeValue(element, VALUE_ATTRIBUTE));
						list.add(() -> addRelation(id, ids, attributes, (c, d) -> organization.addHas(c, d, value)));
					} catch (final NumberFormatException e) {
						throw new XMLStreamException(e);
					}
				} else if (POSSESSES_ELEMENT.equals(name.getLocalPart())) {
					final List<String> ids = collectChild(reader, name);
					try {
						final double value = Double.valueOf(getAttributeValue(element, SCORE_ATTRIBUTE));
						list.add(() -> addRelation(id, ids, capabilities, (c, d) -> organization.addPossesses(c, d, value)));
					} catch (final NumberFormatException e) {
						throw new XMLStreamException(e);
					}
				}
			} else if (event.isEndElement()) {
				final EndElement element = event.asEndElement();
				if (element.getName().equals(tagName)) {
					return;
				}
			}
		}
		throw new XMLStreamException(E.MISSING_END_TAG.get(tagName)); // should not happen as XMLEventReader will do it for us
	}

	private void parseAssignment(final Organization organization, final StartElement element, final Map<String, UniqueId<Agent>> agents,
			final Map<String, UniqueId<InstanceGoal>> instanceGoals, final Map<String, UniqueId<Role>> roles, final List<RunLater> list) {
		/* construction of an assignment depends on the existence of the instance goal */
		list.add(() -> {
			final String agentId = getAttributeValue(element, AGENT_ATTRIBUTE);
			final String roleId = getAttributeValue(element, ROLE_ATTRIBUTE);
			final String goalId = getAttributeValue(element, GOAL_ATTRIBUTE);
			final Agent agent = organization.getAgent(agents.get(agentId));
			if (agent == null) {
				throw new XMLStreamException(E.INCOMPLETE_XML_FILE.get(Agent.class.getSimpleName(), agentId));
			}
			final Role role = organization.getRole(roles.get(roleId));
			if (role == null) {
				throw new XMLStreamException(E.INCOMPLETE_XML_FILE.get(Role.class.getSimpleName(), roleId));
			}
			final InstanceGoal goal = organization.getInstanceGoal(instanceGoals.get(goalId));
			if (goal == null) {
				throw new XMLStreamException(E.INCOMPLETE_XML_FILE.get(InstanceGoal.class.getSimpleName(), goalId));
			}
			final Assignment assignment = assignmentFactory.buildAssignment(agent, role, goal);
			organization.addAssignment(assignment);
		});
	}

	private void parseInstanceGoal(final Organization organization, final StartElement element,
			final Map<String, UniqueId<SpecificationGoal>> specificationGoals, final Map<String, UniqueId<InstanceGoal>> instanceGoals,
			final List<RunLater> list) {
		/* construction of an instance goal depends on the existence of the specification goal */
		list.add(() -> {
			final String id = getAttributeValue(element, SPECIFICATION_ATTRIBUTE);
			final SpecificationGoal goal = organization.getSpecificationGoal(specificationGoals.get(id));
			if (goal == null) {
				throw new XMLStreamException(E.INCOMPLETE_XML_FILE.get(SpecificationGoal.class.getSimpleName(), id));
			}
			// TODO parse parameter
			final InstanceGoal.Parameter parameter = new InstanceGoal.Parameter() {};
			build(InstanceGoal.class, instanceGoals, element, f -> entityFactory.buildInstanceGoal(f, goal, parameter), organization::addInstanceGoal);
		});
	}

	private void parsePmf(final Organization organization, final XMLEventReader reader, final QName tagName, final UniqueId<Pmf> id,
			final Map<String, UniqueId<Attribute>> attributes, final List<RunLater> list) throws XMLStreamException {
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final QName name = element.getName();
				if (MODERATES_ELEMENT.equals(name.getLocalPart())) {
					final List<String> ids = collectChild(reader, name);
					list.add(() -> addRelation(id, ids, attributes, organization::addModerates));
				}
			} else if (event.isEndElement()) {
				final EndElement element = event.asEndElement();
				if (element.getName().equals(tagName)) {
					return;
				}
			}
		}
		throw new XMLStreamException(E.MISSING_END_TAG.get(tagName)); // should not happen as XMLEventReader will do it for us
	}

	private void parseRole(final Organization organization, final XMLEventReader reader, final QName tagName, final UniqueId<Role> id,
			final Map<String, UniqueId<Attribute>> attributes, final Map<String, UniqueId<Capability>> capabilities,
			final Map<String, UniqueId<Characteristic>> characteristics, final Map<String, UniqueId<SpecificationGoal>> goals, final List<RunLater> list)
			throws XMLStreamException {
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final QName name = element.getName();
				if (ACHIEVES_ELEMENT.equals(name.getLocalPart())) {
					final List<String> ids = collectChild(reader, name);
					list.add(() -> addRelation(id, ids, goals, organization::addAchieves));
				} else if (CONTAINS_ELEMENT.equals(name.getLocalPart())) {
					final List<String> ids = collectChild(reader, name);
					try {
						final double value = Double.valueOf(getAttributeValue(element, VALUE_ATTRIBUTE));
						list.add(() -> addRelation(id, ids, characteristics, (c, d) -> organization.addContains(c, d, value)));
					} catch (final NumberFormatException e) {
						throw new XMLStreamException(e);
					}
				} else if (NEEDS_ELEMENT.equals(name.getLocalPart())) {
					final List<String> ids = collectChild(reader, name);
					list.add(() -> addRelation(id, ids, attributes, organization::addNeeds));
				} else if (REQUIRES_ELEMENT.equals(name.getLocalPart())) {
					final List<String> ids = collectChild(reader, name);
					list.add(() -> addRelation(id, ids, capabilities, organization::addRequires));
				}
			} else if (event.isEndElement()) {
				final EndElement element = event.asEndElement();
				if (element.getName().equals(tagName)) {
					return;
				}
			}
		}
		throw new XMLStreamException(E.MISSING_END_TAG.get(tagName)); // should not happen as XMLEventReader will do it for us
	}

	private <T, U> void addRelation(final T t, final List<String> ids, final Map<String, U> map, final BiConsumer<T, U> c) throws XMLStreamException {
		for (final String id : ids) {
			final U u = map.get(id);
			if (u == null) {
				throw new XMLStreamException(E.INCOMPLETE_XML_FILE.get(t.getClass().getSimpleName(), id));
			}
			c.accept(t, u);
		}
	}

	private List<String> collectChild(final XMLEventReader reader, final QName tagName) throws XMLStreamException {
		final List<String> ids = new ArrayList<>();
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final QName name = element.getName();
				if (CHILD_ELEMENT.equals(name.getLocalPart())) {
					ids.add(reader.nextEvent().asCharacters().getData());
				}
			} else if (event.isEndElement()) {
				final EndElement element = event.asEndElement();
				if (element.getName().equals(tagName)) {
					return ids;
				}
			}
		}
		throw new XMLStreamException(E.MISSING_END_TAG.get(tagName)); // should not happen as XMLEventReader will do it for us
	}

}
