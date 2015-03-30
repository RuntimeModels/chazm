package notification;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mockit.Capturing;
import mockit.FullVerifications;
import mockit.integration.junit4.JMockit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings({ "javadoc", "unchecked" })
@RunWith(JMockit.class)
public class MediatorTest {

	static class TestSubscriber implements Subscriber {
		/* (a1, a2, a3, a4), b1, and c1 is detected as correct */
		@Subscribe
		public void a1(final String s) {} // valid but same as a2, a3, and a4

		@Subscribe
		public void a2(final String s) {} // valid but same as a1, a3, and a4

		@Subscribe
		public void a3(final String s) {} // valid but same as a1, a2, and a4

		@Subscribe
		public void a4(final String s) {} // valid but same as a1, a2, and a3

		@Subscribe
		void b1(final Integer i) {} // valid

		@Subscribe
		void c1(final Collection<Integer> c) {} // valid

		@Subscribe
		void d1() {} // invalid, no parameter

		@Subscribe
		void d2(final String s, final Integer i) {} // invalid, more than one parameter

		@Subscribe
		void d3(final String s, final Integer i, final Double d) {} // invalid, more than one parameter

		void e1(final Double s) {} // invalid, not annotated with @Subscribe
	}

	private final Injector injector = Guice.createInjector(new NotificationModule());
	private final Provider<DefaultMediator> provider = injector.getProvider(DefaultMediator.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testMediator() {
		final Mediator m1 = provider.get();
		final Mediator m2 = provider.get();

		assertThat(m1, is(not(nullValue())));
		assertThat(m1, is(sameInstance(m2)));
	}

	@Test
	public void testPost() {
		final DefaultMediator m1 = provider.get();
		final String string = "s1";
		final Integer integer = 13;
		final Collection<String> collection = new ArrayList<>();
		final Map<Subscriber, Integer> mapS = new HashMap<>();
		final Map<Subscriber, Integer> mapI = new HashMap<>();
		final Subscriber s1 = new Subscriber() {
			@Subscribe
			public void a(final String s) {
				mapS.put(this, mapS.computeIfAbsent(this, f -> Integer.valueOf(0)) + 1);
				assertThat(s, is(equalTo(string)));
			}

			@Subscribe
			public void a(final Integer i) {
				mapI.put(this, mapI.computeIfAbsent(this, f -> Integer.valueOf(0)) + 1);
				assertThat(i, is(equalTo(integer)));
			}
		};
		final Subscriber s2 = new Subscriber() {
			@Subscribe
			public void a(final Integer i) {
				mapI.put(this, mapI.computeIfAbsent(this, f -> Integer.valueOf(0)) + 1);
				assertThat(i, is(equalTo(integer)));
			}
		};
		m1.register(s1, s2);

		m1.post(string);
		m1.post(integer);
		m1.post(collection);

		assertThat(mapS.get(s1), is(equalTo(1)));
		assertThat(mapS.get(s2), is(anyOf(equalTo(0), nullValue())));
		assertThat(mapI.get(s1), is(equalTo(1)));
		assertThat(mapI.get(s2), is(equalTo(1)));
	}

	@Test
	public void testPost1() {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new Subscriber() {
			@Subscribe
			public void a(final String s) {
				assertThat(s, is(equalTo("")));
				throw new IllegalArgumentException();
			}
		};
		m1.register(s1);

		m1.post("");

		assertThat(m1.getEvents().size(), is(equalTo(0)));
		assertThat(m1.getSubscribers().size(), is(equalTo(0)));
	}

	@Test
	public void testPost2() {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new Subscriber() {
			@Subscribe
			public void a(final String s) {
				assertThat(s, is(equalTo("")));
				throw new IllegalArgumentException();
			}

			@Subscribe
			public void a(final Integer i) {
				assertThat(i, is(equalTo(1)));
			}
		};
		m1.register(s1);

		m1.post("");
		m1.post(1);

		assertThat(m1.getEvents().size(), is(equalTo(1)));
		assertThat(m1.getSubscribers().size(), is(equalTo(1)));
	}

	@Test
	public void testPost3() {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new Subscriber() {
			@Subscribe
			public void a(final String s) {
				assertThat(s, is(equalTo("")));
				throw new IllegalArgumentException();
			}
		};
		final Subscriber s2 = new Subscriber() {
			@Subscribe
			public void a(final String s) {
				assertThat(s, is(equalTo("")));
			}
		};
		m1.register(s1, s2);

		m1.post("");

		assertThat(m1.getSubscribers().size(), is(equalTo(1)));
		assertThat(m1.getEvents().size(), is(equalTo(1)));
	}

	@Test
	public void testPost4() {
		final DefaultMediator m1 = provider.get();

		m1.post("");

	}

	@Test
	public void testPost5() {
		final DefaultMediator m1 = provider.get();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Parameter (arg0) cannot be null");

		m1.post(null);
	}

	@Test
	public void testRegister(@Capturing final Logger logger) {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new TestSubscriber();

		m1.register(s1);

		final Set<Class<?>> events = m1.getEvents();
		assertThat(events.size(), is(equalTo(3)));
		assertThat(events, hasItems(String.class, Integer.class, Collection.class));
		final Set<Subscriber> subscribers = m1.getSubscribers();
		assertThat(subscribers.size(), is(equalTo(1)));
		assertThat(subscribers, hasItem(s1));
		final Map<Class<?>, Method> s1Events = m1.getSubscriberEvents(s1);
		assertThat(s1Events.size(), is(equalTo(3)));
		assertThat(s1Events.get(String.class), is(not(nullValue())));
		assertThat(s1Events.get(String.class).getName(), is(anyOf(equalTo("a1"), equalTo("a2"), equalTo("a3"), equalTo("a4"))));
		assertThat(s1Events.get(Integer.class), is(not(nullValue())));
		assertThat(s1Events.get(Integer.class).getName(), is(equalTo("b1")));
		assertThat(s1Events.get(Collection.class), is(not(nullValue())));
		assertThat(s1Events.get(Collection.class).getName(), is(equalTo("c1")));
		new FullVerifications(logger) {
			{
				logger.info("Registering ({}) for ({}) with ({})", s1, String.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s1, Integer.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s1, Collection.class, any);
				times = 1;
				logger.warn("Subscriber ({}) is already registered to ({})", s1, String.class);
				times = 3;
			}
		};
	}

	@Test
	public void testRegister1(@Capturing final Logger logger) {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new TestSubscriber();
		final Subscriber s2 = new TestSubscriber();

		m1.register(s1, s2);

		final Set<Class<?>> events = m1.getEvents();
		assertThat(events.size(), is(equalTo(3)));
		assertThat(events, hasItems(String.class, Integer.class, Collection.class));
		final Set<Subscriber> subscribers = m1.getSubscribers();
		assertThat(subscribers.size(), is(equalTo(2)));
		assertThat(subscribers, hasItems(s1, s2));
		final Map<Class<?>, Method> s1Events = m1.getSubscriberEvents(s1);
		assertThat(s1Events.size(), is(equalTo(3)));
		assertThat(s1Events.get(String.class), is(not(nullValue())));
		assertThat(s1Events.get(String.class).getName(), is(anyOf(equalTo("a1"), equalTo("a2"), equalTo("a3"), equalTo("a4"))));
		assertThat(s1Events.get(Integer.class), is(not(nullValue())));
		assertThat(s1Events.get(Integer.class).getName(), is(equalTo("b1")));
		assertThat(s1Events.get(Collection.class), is(not(nullValue())));
		assertThat(s1Events.get(Collection.class).getName(), is(equalTo("c1")));
		final Map<Class<?>, Method> s2Events = m1.getSubscriberEvents(s1);
		assertThat(s2Events.size(), is(equalTo(3)));
		assertThat(s2Events.get(String.class), is(not(nullValue())));
		assertThat(s2Events.get(String.class).getName(), is(anyOf(equalTo("a1"), equalTo("a2"), equalTo("a3"), equalTo("a4"))));
		assertThat(s2Events.get(Integer.class), is(not(nullValue())));
		assertThat(s2Events.get(Integer.class).getName(), is(equalTo("b1")));
		assertThat(s2Events.get(Collection.class), is(not(nullValue())));
		assertThat(s2Events.get(Collection.class).getName(), is(equalTo("c1")));
		new FullVerifications(logger) {
			{
				logger.info("Registering ({}) for ({}) with ({})", s1, String.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s1, Integer.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s1, Collection.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s2, String.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s2, Integer.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s2, Collection.class, any);
				times = 1;
				logger.warn("Subscriber ({}) is already registered to ({})", s1, String.class);
				times = 3;
				logger.warn("Subscriber ({}) is already registered to ({})", s2, String.class);
				times = 3;
			}
		};
	}

	@Test
	public void testRegister2(@Capturing final Logger logger) {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new TestSubscriber();

		m1.register(s1, s1);

		final Set<Class<?>> events = m1.getEvents();
		assertThat(events.size(), is(equalTo(3)));
		assertThat(events, hasItems(String.class, Integer.class, Collection.class));
		final Set<Subscriber> subscribers = m1.getSubscribers();
		assertThat(subscribers.size(), is(equalTo(1)));
		assertThat(subscribers, hasItem(s1));
		final Map<Class<?>, Method> subscriberEvents = m1.getSubscriberEvents(s1);
		assertThat(subscriberEvents.size(), is(equalTo(3)));
		assertThat(subscriberEvents.get(String.class), is(not(nullValue())));
		assertThat(subscriberEvents.get(String.class).getName(), is(anyOf(equalTo("a1"), equalTo("a2"), equalTo("a3"), equalTo("a4"))));
		assertThat(subscriberEvents.get(Integer.class), is(not(nullValue())));
		assertThat(subscriberEvents.get(Integer.class).getName(), is(equalTo("b1")));
		assertThat(subscriberEvents.get(Collection.class), is(not(nullValue())));
		assertThat(subscriberEvents.get(Collection.class).getName(), is(equalTo("c1")));
		new FullVerifications(logger) {
			{
				logger.info("Registering ({}) for ({}) with ({})", s1, String.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s1, Integer.class, any);
				times = 1;
				logger.info("Registering ({}) for ({}) with ({})", s1, Collection.class, any);
				times = 1;
				logger.warn("Subscriber ({}) is already registered to ({})", s1, Integer.class);
				times = 1;
				logger.warn("Subscriber ({}) is already registered to ({})", s1, Collection.class);
				times = 1;
				logger.warn("Subscriber ({}) is already registered to ({})", s1, String.class);
				times = 7;
			}
		};
	}

	@Test
	public void testRegister3() {
		final DefaultMediator m1 = provider.get();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Parameter (arg0) cannot be null");

		m1.register((Subscriber) null);
	}

	@Test
	public void testUnregister() {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new TestSubscriber();
		m1.register(s1);

		m1.unregister(s1);

		final Set<Class<?>> events = m1.getEvents();
		assertThat(events.size(), is(equalTo(0)));
		assertThat(events, not(hasItems(String.class, Integer.class, Collection.class)));
		final Set<Subscriber> subscribers = m1.getSubscribers();
		assertThat(subscribers.size(), is(equalTo(0)));
		assertThat(subscribers, not(hasItem(s1)));
	}

	@Test
	public void testUnregister1() {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new TestSubscriber();
		final Subscriber s2 = new TestSubscriber();
		m1.register(s1, s2);

		m1.unregister(s1);

		final Set<Class<?>> events = m1.getEvents();
		assertThat(events.size(), is(equalTo(3)));
		assertThat(events, hasItems(String.class, Integer.class, Collection.class));
		final Set<Subscriber> subscribers = m1.getSubscribers();
		assertThat(subscribers.size(), is(equalTo(1)));
		assertThat(subscribers, allOf(not(hasItem(s1)), hasItem(s2)));
	}

	@Test
	public void testUnregister2() {
		final DefaultMediator m1 = provider.get();
		final Subscriber s1 = new TestSubscriber();
		m1.register(s1);

		m1.unregister(s1, s1);

		final Set<Class<?>> events = m1.getEvents();
		assertThat(events.size(), is(equalTo(0)));
		assertThat(events, not(hasItems(String.class, Integer.class, Collection.class)));
		final Set<Subscriber> subscribers = m1.getSubscribers();
		assertThat(subscribers.size(), is(equalTo(0)));
		assertThat(subscribers, not(hasItem(s1)));
	}

	@Test
	public void testUnregister3() {
		final DefaultMediator m1 = provider.get();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Parameter (arg0) cannot be null");

		m1.unregister((Subscriber) null);
	}

}
