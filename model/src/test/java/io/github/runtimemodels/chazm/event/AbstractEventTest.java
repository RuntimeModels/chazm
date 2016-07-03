package io.github.runtimemodels.chazm.event;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

@SuppressWarnings({"javadoc", "serial"})
public class AbstractEventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAbstractEntity() {
        final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {};
        final AbstractEvent e2 = new AbstractEvent(EventCategory.ADDED) {};

        assertThat(e1, is(not(nullValue())));
        assertThat(e1, is(not(sameInstance(e2))));
    }

    @Test
    public void testGetCategory() {
        final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {};
        final AbstractEvent e2 = new AbstractEvent(EventCategory.ADDED) {};

        assertThat(e1.getCategory(), is(equalTo(EventCategory.ADDED)));
        assertThat(e2.getCategory(), is(equalTo(EventCategory.ADDED)));
    }

    @Test
    public void testEquals() {
        final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {};
        final AbstractEvent e2 = new AbstractEvent(EventCategory.CHANGED) {};
        final AbstractEvent e3 = new AbstractEvent(EventCategory.ADDED) {};

        assertThat(e1, is(not(equalTo(e2))));
        assertThat(e1, is(equalTo(e3)));
        assertThat(e1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {};
        final AbstractEvent e2 = new AbstractEvent(EventCategory.CHANGED) {};
        final AbstractEvent e3 = new AbstractEvent(EventCategory.ADDED) {};

        assertThat(e1.hashCode(), is(not(equalTo(e2.hashCode()))));
        assertThat(e1.hashCode(), is(equalTo(e3.hashCode())));
    }

    @Test
    public void testToString() {
        final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {};
        final AbstractEvent e2 = new AbstractEvent(EventCategory.CHANGED) {};
        final AbstractEvent e3 = new AbstractEvent(EventCategory.ADDED) {};

        assertThat(e1.toString(), is(not(equalTo(e2.toString()))));
        assertThat(e1.toString(), is(equalTo(e3.toString())));
    }

}
