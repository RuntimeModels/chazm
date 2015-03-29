package model.organization.event;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.entity.Attribute;
import model.organization.entity.AttributeFactory;
import model.organization.id.IdFactory;
import model.organization.relation.RelationModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class AttributeEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final AttributeEventFactory aef = injector.getInstance(AttributeEventFactory.class);
	private final AttributeFactory af = injector.getInstance(AttributeFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAttributeEvent() {
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final AttributeEvent ae1 = aef.build(EventCategory.ADDED, a);
		final AttributeEvent ae2 = aef.build(EventCategory.ADDED, a);

		assertThat(ae1, is(not(nullValue())));
		assertThat(ae1, is(not(sameInstance(ae2))));
	}

	@Test
	public void testAttributeEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		aef.build(null, null);
	}

	@Test
	public void testAttributeEvent2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (category) cannot be null"));

		new AttributeEvent(null, null);
	}

	@Test
	public void testAttributeEvent3() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		aef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testAttributeEvent4() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (attribute) cannot be null"));

		new AttributeEvent(EventCategory.ADDED, null);
	}

	@Test
	public void testGetId() {
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final AttributeEvent ae = aef.build(EventCategory.ADDED, a);

		assertThat(ae.getId(), is(sameInstance(a.getId())));
	}

	@Test
	public void testEquals() {
		final Attribute a1 = af.buildAttribute(idf.build(Attribute.class, "a1"), Attribute.Type.NEGATIVE_QUALITY);
		final Attribute a2 = af.buildAttribute(idf.build(Attribute.class, "a2"), Attribute.Type.NEGATIVE_QUALITY);
		final Attribute a3 = af.buildAttribute(idf.build(Attribute.class, "a1"), Attribute.Type.NEGATIVE_QUALITY);
		final AttributeEvent ae1 = aef.build(EventCategory.ADDED, a1);
		final AttributeEvent ae2 = aef.build(EventCategory.ADDED, a2);
		final AttributeEvent ae3 = aef.build(EventCategory.ADDED, a3);

		assertThat(ae1, is(not(equalTo(ae2))));
		assertThat(ae1, is(equalTo(ae3)));
		assertThat(ae1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Attribute a1 = af.buildAttribute(idf.build(Attribute.class, "a1"), Attribute.Type.NEGATIVE_QUALITY);
		final Attribute a2 = af.buildAttribute(idf.build(Attribute.class, "a2"), Attribute.Type.NEGATIVE_QUALITY);
		final Attribute a3 = af.buildAttribute(idf.build(Attribute.class, "a1"), Attribute.Type.NEGATIVE_QUALITY);
		final AttributeEvent ae1 = aef.build(EventCategory.ADDED, a1);
		final AttributeEvent ae2 = aef.build(EventCategory.ADDED, a2);
		final AttributeEvent ae3 = aef.build(EventCategory.ADDED, a3);

		assertThat(ae1.hashCode(), is(not(equalTo(ae2.hashCode()))));
		assertThat(ae1.hashCode(), is(equalTo(ae3.hashCode())));
	}

	@Test
	public void testToString() {
		final Attribute a1 = af.buildAttribute(idf.build(Attribute.class, "a1"), Attribute.Type.NEGATIVE_QUALITY);
		final Attribute a2 = af.buildAttribute(idf.build(Attribute.class, "a2"), Attribute.Type.NEGATIVE_QUALITY);
		final Attribute a3 = af.buildAttribute(idf.build(Attribute.class, "a1"), Attribute.Type.NEGATIVE_QUALITY);
		final AttributeEvent ae1 = aef.build(EventCategory.ADDED, a1);
		final AttributeEvent ae2 = aef.build(EventCategory.ADDED, a2);
		final AttributeEvent ae3 = aef.build(EventCategory.ADDED, a3);

		assertThat(ae1.toString(), is(not(equalTo(ae2.toString()))));
		assertThat(ae1.toString(), is(equalTo(ae3.toString())));
	}

}
