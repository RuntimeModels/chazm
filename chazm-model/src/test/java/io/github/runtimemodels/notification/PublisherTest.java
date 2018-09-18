package io.github.runtimemodels.notification;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.runtimemodels.message.E;
import mockit.Capturing;
import mockit.FullVerifications;
import mockit.integration.junit4.JMockit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.inject.Provider;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
@RunWith(JMockit.class)
public class PublisherTest {

    private final Injector injector = Guice.createInjector(new NotificationModule());
    private final Provider<Publisher> provider = injector.getProvider(Publisher.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPublisher() {
        final Publisher p1 = provider.get();
        final Publisher p2 = provider.get();

        assertThat(p1, is(not(nullValue())));
        assertThat(p1, is(sameInstance(p2)));
    }

    //    @Test
    public void testPost(@Capturing final Mediator mediator) {
        final Publisher p1 = provider.get();

        p1.post("OK");
        p1.post("OK");
        p1.post(1);
        p1.post(2);
        p1.post(3);

        new FullVerifications(mediator) {
            {
                mediator.post("OK");
                times = 2;
                mediator.post(1);
                times = 1;
                mediator.post(2);
                times = 1;
                mediator.post(3);
                times = 1;
            }
        };
    }

    @Test
    public void testPost1() {
        final Publisher p1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "post"));

        p1.post(null);
    }

}
