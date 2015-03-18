package model.organization.event;

import java.util.Collection;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

import event.EventModule;
import event.Mediator;
import event.Subscribe;
import event.Subscriber;

@SuppressWarnings("javadoc")
public class MediatorTest {

	private final Injector injector = Guice.createInjector(new EventModule());
	private final Provider<Mediator> provider = injector.getProvider(Mediator.class);

	@Test
	public void testRegister() {
		Mediator m = provider.get();
		Subscriber s1 = new Subscriber() {
			// (e1 or e2 or e3 or e4) and e5 and e6
			@Subscribe
			public void e1(String s) {}

			@Subscribe
			protected void e2(String s) {}

			@Subscribe
			void e3(String s) {}

			@Subscribe
			private void e4(String s) {}

			@Subscribe
			void e5(Integer i) {}

			@Subscribe
			void e6(Collection<Integer> c) {}

			@Subscribe
			void e7(String s, Integer i) {}

			@SuppressWarnings("unused")
			void e8(Double s) {}
		};
		m.register(s1);
	}

}
