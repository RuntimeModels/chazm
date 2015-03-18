package event;

import java.util.Collection;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings("javadoc")
public class MediatorTest {

	private final Injector injector = Guice.createInjector(new EventModule());
	private final Provider<Mediator> provider = injector.getProvider(Mediator.class);

	@Test
	public void testRegister() {
		final Mediator m = provider.get();
		final Subscriber s1 = new Subscriber() {
			// (e1 or e2 or e3 or e4) and e5 and e6
			@Subscribe
			public void e1(final String s) {}

			@Subscribe
			protected void e2(final String s) {}

			@Subscribe
			void e3(final String s) {}

			@Subscribe
			private void e4(final String s) {}

			@Subscribe
			void e5(final Integer i) {}

			@Subscribe
			void e6(final Collection<Integer> c) {}

			@Subscribe
			void e7(final String s, final Integer i) {}

			@SuppressWarnings("unused")
			void e8(final Double s) {}
		};
		m.register(s1);
	}

}
