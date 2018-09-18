package io.github.runtimemodels.notification;

import io.github.runtimemodels.chazm.event.*;
import io.github.runtimemodels.message.L;
import org.slf4j.Logger;

import javax.validation.constraints.NotNull;

class DefaultSubscriber implements Subscriber {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DefaultSubscriber.class);

    @Subscribe
    public void event(@NotNull final AchievesEvent event) {
        log.debug(L.RECEIVED_ROLE_GOAL.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getGoalId());
    }

    @Subscribe
    public void event(@NotNull final AgentEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final AssignmentEvent event) {
        log.debug(L.RECEIVED_AGENT_ROLE_GOAL.get(), event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(), event.getRoleId(),
                event.getGoalId());
    }

    @Subscribe
    public void event(@NotNull final AttributeEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final CapabilityEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final CharacteristicEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final ContainsEvent event) {
        log.debug(L.RECEIVED_ROLE_CHARACTERISTIC_VALUE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(),
                event.getCharacteristicId(), event.getValue());
    }

    @Subscribe
    public void event(@NotNull final HasEvent event) {
        log.debug(L.RECEIVED_AGENT_ATTRIBUTE_VALUE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(), event.getAttributeId(),
                event.getValue());
    }

    @Subscribe
    public void event(@NotNull final InstanceGoalEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final ModeratesEvent event) {
        log.debug(L.RECEIVED_PMF_ATTRIBUTE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getPmfId(), event.getAttributeId());
    }

    @Subscribe
    public void event(@NotNull final NeedsEvent event) {
        log.debug(L.RECEIVED_ROLE_ATTRIBUTE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getAttributeId());
    }

    @Subscribe
    public void event(@NotNull final PmfEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final PolicyEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final PossessesEvent event) {
        log.debug(L.RECEIVED_AGENT_CAPABILITY_SCORE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(),
                event.getCapabilityId(), event.getScore());
    }

    @Subscribe
    public void event(@NotNull final RequiresEvent event) {
        log.debug(L.RECEIVED_ROLE_CAPABILITY.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getCapabilityId());
    }

    @Subscribe
    public void event(@NotNull final RoleEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final SpecificationGoalEvent event) {
        log.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
    }

    @Subscribe
    public void event(@NotNull final UsesEvent event) {
        log.debug(L.RECEIVED_ROLE_PMF.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getPmfId());
    }

}
