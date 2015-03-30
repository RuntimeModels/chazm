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

import model.organization.Organization;
import model.organization.entity.Capability;
import model.organization.entity.EntityFactory;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.id.UniqueId;

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

	private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);
	private static final String ROLE_DIAGRAM_ELEMENT = "RoleDiagram";
	private static final String ROLE_ELEMENT = "Role";
	private static final String GOAL_ELEMENT = "Goal";
	private static final String CAPABILITY_ELEMENT = "Capability";
	private static final String ACHIEVES_ELEMENT = "achieves";
	private static final String REQUIRES_ELEMENT = "requires";
	private static final String ID_ATTRIBUTE = "id";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String CHILD_ELEMENT = "child";

	private final IdFactory idFactory;
	private final EntityFactory entityFactory;

	@Inject
	XmlParser(@NotNull final IdFactory idFactory, @NotNull final EntityFactory entityFactory) {
		this.idFactory = idFactory;
		this.entityFactory = entityFactory;
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
	public void parse(final Organization organization, final InputStream inputStream) throws XMLStreamException {
		final XMLInputFactory factory = XMLInputFactory.newInstance();
		final XMLEventReader reader = factory.createXMLEventReader(inputStream);
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
				final StartElement element = event.asStartElement();
				logger.debug("{}", element);
				final QName name = element.getName();
				if (ROLE_DIAGRAM_ELEMENT.equals(name.getLocalPart())) {
					parseDiagram(organization, reader, name);
				}
			}
		}
	}

	private void parseDiagram(final Organization organization, final XMLEventReader reader, final QName tagName) throws XMLStreamException {
		final Map<String, UniqueId<Capability>> capabilities = new HashMap<>();
		final Map<String, UniqueId<SpecificationGoal>> goals = new HashMap<>();
		final Map<String, UniqueId<Role>> roles = new HashMap<>();
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final QName name = element.getName();
				if (CAPABILITY_ELEMENT.equals(name.getLocalPart())) {
					build(Capability.class, capabilities, f -> entityFactory.buildCapability(f), element, organization::addCapability);
				} else if (GOAL_ELEMENT.equals(name.getLocalPart())) {
					build(SpecificationGoal.class, goals, f -> entityFactory.buildSpecificationGoal(f), element, organization::addSpecificationGoal);
				} else if (ROLE_ELEMENT.equals(name.getLocalPart())) {
					final Role role = build(Role.class, roles, f -> entityFactory.buildRole(f), element, organization::addRole);
					parseRole(organization, reader, name, role, capabilities, goals);
				}
				// TODO add the other entities
			} else if (event.isEndElement()) {
				final EndElement element = event.asEndElement();
				if (element.getName().equals(tagName)) {
					return;
				}
			}
		}
	}

	private <T> T build(final Class<T> clazz, final Map<String, UniqueId<T>> map, final Function<UniqueId<T>, T> f, final StartElement element,
			final Consumer<T> c) {
		final UniqueId<T> id = idFactory.build(clazz, element.getAttributeByName(new QName(NAME_ATTRIBUTE)).getValue());
		map.put(element.getAttributeByName(new QName(ID_ATTRIBUTE)).getValue(), id);
		final T t = f.apply(id);
		c.accept(t);
		return t;
	}

	private void parseRole(final Organization organization, final XMLEventReader reader, final QName tagName, final Role role,
			final Map<String, UniqueId<Capability>> capabilities, final Map<String, UniqueId<SpecificationGoal>> goals) throws XMLStreamException {
		while (reader.hasNext()) {
			final XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				final StartElement element = event.asStartElement();
				final QName name = element.getName();
				if (ACHIEVES_ELEMENT.equals(name.getLocalPart())) {
					addRelation(reader, name, role.getId(), goals, organization::addAchieves);
				} else if (REQUIRES_ELEMENT.equals(name.getLocalPart())) {
					addRelation(reader, name, role.getId(), capabilities, organization::addRequires);
				}
				// TODO add the other relations
			} else if (event.isEndElement()) {
				final EndElement element = event.asEndElement();
				if (element.getName().equals(tagName)) {
					return;
				}
			}
		}
	}

	private <T, U> void addRelation(final XMLEventReader reader, final QName tagName, final T t, final Map<String, U> map, final BiConsumer<T, U> c)
			throws XMLStreamException {
		final List<String> ids = collectChild(reader, tagName);
		for (final String id : ids) {
			final U u = map.get(id);
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
		return ids;
	}

}
