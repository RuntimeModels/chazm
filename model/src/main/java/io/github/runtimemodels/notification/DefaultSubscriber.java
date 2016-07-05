package io.github.runtimemodels.notification;

import io.github.runtimemodels.chazm.event.AchievesEvent;
import io.github.runtimemodels.chazm.event.AgentEvent;
import io.github.runtimemodels.chazm.event.AssignmentEvent;
import io.github.runtimemodels.chazm.event.AttributeEvent;
import io.github.runtimemodels.chazm.event.CapabilityEvent;
import io.github.runtimemodels.chazm.event.CharacteristicEvent;
import io.github.runtimemodels.chazm.event.ContainsEvent;
import io.github.runtimemodels.chazm.event.HasEvent;
import io.github.runtimemodels.chazm.event.InstanceGoalEvent;
import io.github.runtimemodels.chazm.event.ModeratesEvent;
import io.github.runtimemodels.chazm.event.NeedsEvent;
import io.github.runtimemodels.chazm.event.PmfEvent;
import io.github.runtimemodels.chazm.event.PolicyEvent;
import io.github.runtimemodels.chazm.event.PossessesEvent;
import io.github.runtimemodels.chazm.event.RequiresEvent;
import io.github.runtimemodels.chazm.event.RoleEvent;
import io.github.runtimemodels.chazm.event.SpecificationGoalEvent;
import io.github.runtimemodels.chazm.event.UsesEvent;
import io.github.runtimemodels.message.L;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Slf4j
class DefaultSubscriber implements Subscriber {

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
