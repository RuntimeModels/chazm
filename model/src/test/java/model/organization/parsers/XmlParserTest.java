package model.organization.parsers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import javax.inject.Provider;
import javax.xml.stream.XMLStreamException;

import model.organization.Organization;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class XmlParserTest {

	private final Injector injector = Guice.createInjector(new ParsersModule());
	private final Provider<XmlParser> provider = injector.getProvider(XmlParser.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testXmlParser() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final XmlParser p2 = provider.get();

		assertThat(p1, is(not(nullValue())));
		assertThat(p1, is(sameInstance(p2)));
	}

	@Test
	public void testParse() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram1.xml");

		p1.parse(o1, is);

	}

}
