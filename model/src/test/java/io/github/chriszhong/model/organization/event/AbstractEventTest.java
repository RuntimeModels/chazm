package io.github.chriszhong.model.organization.event;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings({ "javadoc", "serial" })
public class AbstractEventTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAbstractEntity() {
		final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = -5967072821374110113L;
		};
		final AbstractEvent e2 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = -9146983573327511688L;
		};

		assertThat(e1, is(not(nullValue())));
		assertThat(e1, is(not(sameInstance(e2))));
	}

	@Test
	public void testGetCategory() {
		final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = 1592375564043879681L;
		};
		final AbstractEvent e2 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = -9063642256808489839L;
		};

		assertThat(e1.getCategory(), is(equalTo(EventCategory.ADDED)));
		assertThat(e2.getCategory(), is(equalTo(EventCategory.ADDED)));
	}

	@Test
	public void testEquals() {
		final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = -9168047121418608726L;
		};
		final AbstractEvent e2 = new AbstractEvent(EventCategory.CHANGED) {

			/**
			 *
			 */
			private static final long serialVersionUID = 5600465025225807257L;
		};
		final AbstractEvent e3 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = 4946535901286600550L;
		};

		assertThat(e1, is(not(equalTo(e2))));
		assertThat(e1, is(equalTo(e3)));
		assertThat(e1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = 7074854056858787505L;
		};
		final AbstractEvent e2 = new AbstractEvent(EventCategory.CHANGED) {

			/**
			 *
			 */
			private static final long serialVersionUID = 6302284847868730221L;
		};
		final AbstractEvent e3 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = -8545084576637348885L;
		};

		assertThat(e1.hashCode(), is(not(equalTo(e2.hashCode()))));
		assertThat(e1.hashCode(), is(equalTo(e3.hashCode())));
	}

	@Test
	public void testToString() {
		final AbstractEvent e1 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = -9199510580363302347L;
		};
		final AbstractEvent e2 = new AbstractEvent(EventCategory.CHANGED) {

			/**
			 *
			 */
			private static final long serialVersionUID = 8561740404683107659L;
		};
		final AbstractEvent e3 = new AbstractEvent(EventCategory.ADDED) {

			/**
			 *
			 */
			private static final long serialVersionUID = 6438896537236981318L;
		};

		assertThat(e1.toString(), is(not(equalTo(e2.toString()))));
		assertThat(e1.toString(), is(equalTo(e3.toString())));
	}

}
