package notification;

import javax.validation.constraints.NotNull;

import message.L;
import model.organization.event.AchievesEvent;
import model.organization.event.AgentEvent;
import model.organization.event.AssignmentEvent;
import model.organization.event.AttributeEvent;
import model.organization.event.CapabilityEvent;
import model.organization.event.CharacteristicEvent;
import model.organization.event.ContainsEvent;
import model.organization.event.HasEvent;
import model.organization.event.InstanceGoalEvent;
import model.organization.event.ModeratesEvent;
import model.organization.event.NeedsEvent;
import model.organization.event.PmfEvent;
import model.organization.event.PolicyEvent;
import model.organization.event.PossessesEvent;
import model.organization.event.RequiresEvent;
import model.organization.event.RoleEvent;
import model.organization.event.SpecificationGoalEvent;
import model.organization.event.UsesEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultSubscriber implements Subscriber {

	private static final Logger logger = LoggerFactory.getLogger(DefaultSubscriber.class);

	@Subscribe
	public void event(@NotNull final AchievesEvent event) {
		logger.debug(L.RECEIVED_ROLE_GOAL.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getGoalId());
	}

	@Subscribe
	public void event(@NotNull final AgentEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final AssignmentEvent event) {
		logger.debug(L.RECEIVED_AGENT_ROLE_GOAL.get(), event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(), event.getRoleId(),
				event.getGoalId());
	}

	@Subscribe
	public void event(@NotNull final AttributeEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final CapabilityEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final CharacteristicEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final ContainsEvent event) {
		logger.debug(L.RECEIVED_ROLE_CHARACTERISTIC_VALUE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(),
				event.getCharacteristicId(), event.getValue());
	}

	@Subscribe
	public void event(@NotNull final HasEvent event) {
		logger.debug(L.RECEIVED_AGENT_ATTRIBUTE_VALUE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(), event.getAttributeId(),
				event.getValue());
	}

	@Subscribe
	public void event(@NotNull final InstanceGoalEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final ModeratesEvent event) {
		logger.debug(L.RECEIVED_PMF_ATTRIBUTE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getPmfId(), event.getAttributeId());
	}

	@Subscribe
	public void event(@NotNull final NeedsEvent event) {
		logger.debug(L.RECEIVED_ROLE_ATTRIBUTE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getAttributeId());
	}

	@Subscribe
	public void event(@NotNull final PmfEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final PolicyEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final PossessesEvent event) {
		logger.debug(L.RECEIVED_AGENT_CAPABILITY_SCORE.get(), event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(),
				event.getCapabilityId(), event.getScore());
	}

	@Subscribe
	public void event(@NotNull final RequiresEvent event) {
		logger.debug(L.RECEIVED_ROLE_CAPABILITY.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getCapabilityId());
	}

	@Subscribe
	public void event(@NotNull final RoleEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final SpecificationGoalEvent event) {
		logger.debug(L.RECEIVED_ID.get(), event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(@NotNull final UsesEvent event) {
		logger.debug(L.RECEIVED_ROLE_PMF.get(), event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getPmfId());
	}

}
