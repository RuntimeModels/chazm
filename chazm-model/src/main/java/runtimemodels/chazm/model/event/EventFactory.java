package runtimemodels.chazm.model.event;

/**
 * The {@linkplain EventFactory} interface defines the APIs for constructing events.
 *
 * @author Christopher Zhong
 * @see AchievesEventFactory
 * @see AgentEventFactory
 * @see AssignmentEventFactory
 * @see AttributeEventFactory
 * @see CapabilityEventFactory
 * @see CharacteristicEventFactory
 * @see ContainsEventFactory
 * @see HasEventFactory
 * @see InstanceGoalEventFactory
 * @see ModeratesEventFactory
 * @see NeedsEventFactory
 * @see PmfEventFactory
 * @see PolicyEventFactory
 * @see PossessesEventFactory
 * @see RequiresEventFactory
 * @see RoleEventFactory
 * @see SpecificationGoalEventFactory
 * @see UsesEventFactory
 * @since 7.0.0
 */
public interface EventFactory extends AchievesEventFactory, AgentEventFactory, AssignmentEventFactory, AttributeEventFactory, CapabilityEventFactory,
        CharacteristicEventFactory, ContainsEventFactory, HasEventFactory, InstanceGoalEventFactory, ModeratesEventFactory, NeedsEventFactory, PmfEventFactory,
        PolicyEventFactory, PossessesEventFactory, RequiresEventFactory, RoleEventFactory, SpecificationGoalEventFactory, UsesEventFactory {}
