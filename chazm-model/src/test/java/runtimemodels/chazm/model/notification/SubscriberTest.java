package runtimemodels.chazm.model.notification;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import mockit.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import runtimemodels.chazm.api.entity.*;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.message.E;
import runtimemodels.chazm.model.event.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
//@RunWith(JMockit.class)
public class SubscriberTest {

    abstract class MockMediator implements Mediator {

        @Override
        public void register(Subscriber subscriber) {
        }

        @Override
        public void unregister(Subscriber subscriber) {
        }
    }

    private final Injector injector = Guice.createInjector(new NotificationModule());
    private final Provider<DefaultSubscriber> provider = injector.getProvider(DefaultSubscriber.class);
    @Capturing
    private Logger logger;
    @Mocked
    private UniqueId<Agent> agentId;
    @Mocked
    private UniqueId<Attribute> attributeId;
    @Mocked
    private UniqueId<Capability> capabilityId;
    @Mocked
    private UniqueId<Characteristic> characteristicId;
    @Mocked
    private UniqueId<InstanceGoal> instGoalId;
    @Mocked
    private UniqueId<Pmf> pmfId;
    @Mocked
    private UniqueId<Policy> policyId;
    @Mocked
    private UniqueId<Role> roleId;
    @Mocked
    private UniqueId<SpecificationGoal> specGoalId;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSubscriber() {
        final Subscriber s1 = provider.get();
        final Subscriber s2 = provider.get();

        assertThat(s1, is(not(nullValue())));
        assertThat(s1, is(not(sameInstance(s2))));
    }

    //    @Test
    public void testEvent1(@Mocked final AchievesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getGoalId();
                result = specGoalId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Override
            public <T> void post(final T event) {
                s1.event((AchievesEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, goal = {}", AchievesEvent.class.getSimpleName(), EventType.ADDED, roleId, specGoalId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, goal = {}", AchievesEvent.class.getSimpleName(), EventType.UPDATED, roleId, specGoalId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, goal = {}", AchievesEvent.class.getSimpleName(), EventType.REMOVED, roleId, specGoalId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent2(@Mocked final AgentEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = agentId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((AgentEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", AgentEvent.class.getSimpleName(), EventType.ADDED, agentId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", AgentEvent.class.getSimpleName(), EventType.UPDATED, agentId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", AgentEvent.class.getSimpleName(), EventType.REMOVED, agentId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent3(@Mocked final AssignmentEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getAgentId();
                result = agentId;
                event.getRoleId();
                result = roleId;
                event.getGoalId();
                result = instGoalId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((AssignmentEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): agent = {}, role = {}, goal = {}", AssignmentEvent.class.getSimpleName(), EventType.ADDED, agentId,
                        roleId, instGoalId);
                times = 2;
                logger.debug("Received ({}, {}): agent = {}, role = {}, goal = {}", AssignmentEvent.class.getSimpleName(), EventType.UPDATED, agentId,
                        roleId, instGoalId);
                times = 1;
                logger.debug("Received ({}, {}): agent = {}, role = {}, goal = {}", AssignmentEvent.class.getSimpleName(), EventType.REMOVED, agentId,
                        roleId, instGoalId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent4(@Mocked final AttributeEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = attributeId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((AttributeEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", AttributeEvent.class.getSimpleName(), EventType.ADDED, attributeId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", AttributeEvent.class.getSimpleName(), EventType.UPDATED, attributeId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", AttributeEvent.class.getSimpleName(), EventType.REMOVED, attributeId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent5(@Mocked final CapabilityEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = capabilityId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((CapabilityEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", CapabilityEvent.class.getSimpleName(), EventType.ADDED, capabilityId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", CapabilityEvent.class.getSimpleName(), EventType.UPDATED, capabilityId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", CapabilityEvent.class.getSimpleName(), EventType.REMOVED, capabilityId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent6(@Mocked final CharacteristicEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = characteristicId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((CharacteristicEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", CharacteristicEvent.class.getSimpleName(), EventType.ADDED, characteristicId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", CharacteristicEvent.class.getSimpleName(), EventType.UPDATED, characteristicId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", CharacteristicEvent.class.getSimpleName(), EventType.REMOVED, characteristicId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent7(@Mocked final ContainsEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getCharacteristicId();
                result = characteristicId;
                event.getValue();
                result = 0.25;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((ContainsEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, characteristic = {}, value = {}", ContainsEvent.class.getSimpleName(), EventType.ADDED, roleId,
                        characteristicId, 0.25);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, characteristic = {}, value = {}", ContainsEvent.class.getSimpleName(), EventType.UPDATED,
                        roleId, characteristicId, 0.25);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, characteristic = {}, value = {}", ContainsEvent.class.getSimpleName(), EventType.REMOVED,
                        roleId, characteristicId, 0.25);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent8(@Mocked final HasEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getAgentId();
                result = agentId;
                event.getAttributeId();
                result = attributeId;
                event.getValue();
                result = 0.7;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((HasEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): agent = {}, attribute = {}, value = {}", HasEvent.class.getSimpleName(), EventType.ADDED, agentId,
                        attributeId, 0.7);
                times = 2;
                logger.debug("Received ({}, {}): agent = {}, attribute = {}, value = {}", HasEvent.class.getSimpleName(), EventType.UPDATED, agentId,
                        attributeId, 0.7);
                times = 1;
                logger.debug("Received ({}, {}): agent = {}, attribute = {}, value = {}", HasEvent.class.getSimpleName(), EventType.REMOVED, agentId,
                        attributeId, 0.7);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent9(@Mocked final InstanceGoalEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = instGoalId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((InstanceGoalEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", InstanceGoalEvent.class.getSimpleName(), EventType.ADDED, instGoalId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", InstanceGoalEvent.class.getSimpleName(), EventType.UPDATED, instGoalId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", InstanceGoalEvent.class.getSimpleName(), EventType.REMOVED, instGoalId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent10(@Mocked final ModeratesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getPmfId();
                result = pmfId;
                event.getAttributeId();
                result = attributeId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((ModeratesEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): pmf = {}, attribute = {}", ModeratesEvent.class.getSimpleName(), EventType.ADDED, pmfId, attributeId);
                times = 2;
                logger.debug("Received ({}, {}): pmf = {}, attribute = {}", ModeratesEvent.class.getSimpleName(), EventType.UPDATED, pmfId, attributeId);
                times = 1;
                logger.debug("Received ({}, {}): pmf = {}, attribute = {}", ModeratesEvent.class.getSimpleName(), EventType.REMOVED, pmfId, attributeId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent11(@Mocked final NeedsEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getAttributeId();
                result = attributeId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((NeedsEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, attribute = {}", NeedsEvent.class.getSimpleName(), EventType.ADDED, roleId, attributeId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, attribute = {}", NeedsEvent.class.getSimpleName(), EventType.UPDATED, roleId, attributeId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, attribute = {}", NeedsEvent.class.getSimpleName(), EventType.REMOVED, roleId, attributeId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent12(@Mocked final PmfEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = pmfId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((PmfEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", PmfEvent.class.getSimpleName(), EventType.ADDED, pmfId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", PmfEvent.class.getSimpleName(), EventType.UPDATED, pmfId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", PmfEvent.class.getSimpleName(), EventType.REMOVED, pmfId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent13(@Mocked final PolicyEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = policyId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((PolicyEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", PolicyEvent.class.getSimpleName(), EventType.ADDED, policyId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", PolicyEvent.class.getSimpleName(), EventType.UPDATED, policyId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", PolicyEvent.class.getSimpleName(), EventType.REMOVED, policyId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent14(@Mocked final PossessesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getAgentId();
                result = agentId;
                event.getCapabilityId();
                result = capabilityId;
                event.getScore();
                result = 0.8;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((PossessesEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): agent = {}, capability = {}, score = {}", PossessesEvent.class.getSimpleName(), EventType.ADDED, agentId,
                        capabilityId, 0.8);
                times = 2;
                logger.debug("Received ({}, {}): agent = {}, capability = {}, score = {}", PossessesEvent.class.getSimpleName(), EventType.UPDATED,
                        agentId, capabilityId, 0.8);
                times = 1;
                logger.debug("Received ({}, {}): agent = {}, capability = {}, score = {}", PossessesEvent.class.getSimpleName(), EventType.REMOVED,
                        agentId, capabilityId, 0.8);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent15(@Mocked final RequiresEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getCapabilityId();
                result = capabilityId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((RequiresEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, capability = {}", RequiresEvent.class.getSimpleName(), EventType.ADDED, roleId, capabilityId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, capability = {}", RequiresEvent.class.getSimpleName(), EventType.UPDATED, roleId, capabilityId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, capability = {}", RequiresEvent.class.getSimpleName(), EventType.REMOVED, roleId, capabilityId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent16(@Mocked final RoleEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = roleId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((RoleEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", RoleEvent.class.getSimpleName(), EventType.ADDED, roleId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", RoleEvent.class.getSimpleName(), EventType.UPDATED, roleId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", RoleEvent.class.getSimpleName(), EventType.REMOVED, roleId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent17(@Mocked final SpecificationGoalEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getId();
                result = specGoalId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((SpecificationGoalEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", SpecificationGoalEvent.class.getSimpleName(), EventType.ADDED, specGoalId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", SpecificationGoalEvent.class.getSimpleName(), EventType.UPDATED, specGoalId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", SpecificationGoalEvent.class.getSimpleName(), EventType.REMOVED, specGoalId);
                times = 1;
            }
        };
    }

    //    @Test
    public void testEvent18(@Mocked final UsesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventType.ADDED, EventType.ADDED, EventType.UPDATED, EventType.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getPmfId();
                result = pmfId;
            }
        };
        final Mediator mockMediator = new MockMediator() {

            @Mock
            public <T> void post(final T event) {
                s1.event((UsesEvent) event);
            }

        };

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, pmf = {}", UsesEvent.class.getSimpleName(), EventType.ADDED, roleId, pmfId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, pmf = {}", UsesEvent.class.getSimpleName(), EventType.UPDATED, roleId, pmfId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, pmf = {}", UsesEvent.class.getSimpleName(), EventType.REMOVED, roleId, pmfId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent19() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AchievesEvent) null);
    }

    @Test
    public void testEvent20() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AgentEvent) null);
    }

    @Test
    public void testEvent21() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AssignmentEvent) null);
    }

    @Test
    public void testEvent22() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AttributeEvent) null);
    }

    @Test
    public void testEvent23() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((CapabilityEvent) null);
    }

    @Test
    public void testEvent24() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((CharacteristicEvent) null);
    }

    @Test
    public void testEvent25() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((ContainsEvent) null);
    }

    @Test
    public void testEvent26() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((HasEvent) null);
    }

    @Test
    public void testEvent27() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((InstanceGoalEvent) null);
    }

    @Test
    public void testEvent28() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((ModeratesEvent) null);
    }

    @Test
    public void testEvent29() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((NeedsEvent) null);
    }

    @Test
    public void testEvent30() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((PmfEvent) null);
    }

    @Test
    public void testEvent31() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((PolicyEvent) null);
    }

    @Test
    public void testEvent32() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((PossessesEvent) null);
    }

    @Test
    public void testEvent33() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((RequiresEvent) null);
    }

    @Test
    public void testEvent34() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((RoleEvent) null);
    }

    @Test
    public void testEvent35() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((SpecificationGoalEvent) null);
    }

    @Test
    public void testEvent36() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((UsesEvent) null);
    }

}
