package notification;

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
	public void event(final AchievesEvent event) {
		logger.debug("Received ({}, {}): role = {}, goal = {}", event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getGoalId());
	}

	@Subscribe
	public void event(final AgentEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final AssignmentEvent event) {
		logger.debug("Received ({}, {}): agent = {}, role = {}, goal = {}", event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(),
				event.getRoleId(), event.getGoalId());
	}

	@Subscribe
	public void event(final AttributeEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final CapabilityEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final CharacteristicEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final ContainsEvent event) {
		logger.debug("Received ({}, {}): role = {}, characteristic = {}, value = {}", event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(),
				event.getCharacteristicId(), event.getValue());
	}

	@Subscribe
	public void event(final HasEvent event) {
		logger.debug("Received ({}, {}): agent = {}, attribute = {}, value = {}", event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(),
				event.getAttributeId(), event.getValue());
	}

	@Subscribe
	public void event(final InstanceGoalEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final ModeratesEvent event) {
		logger.debug("Received ({}, {}): pmf = {}, attribute = {}", event.getClass().getSimpleName(), event.getCategory(), event.getPmfId(),
				event.getAttributeId());
	}

	@Subscribe
	public void event(final NeedsEvent event) {
		logger.debug("Received ({}, {}): role = {}, attribute = {}", event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(),
				event.getAttributeId());
	}

	@Subscribe
	public void event(final PmfEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final PolicyEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final PossessesEvent event) {
		logger.debug("Received ({}, {}): agent = {}, capability = {}, score = {}", event.getClass().getSimpleName(), event.getCategory(), event.getAgentId(),
				event.getCapabilityId(), event.getScore());
	}

	@Subscribe
	public void event(final RequiresEvent event) {
		logger.debug("Received ({}, {}): role = {}, capability = {}", event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(),
				event.getCapabilityId());
	}

	@Subscribe
	public void event(final RoleEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final SpecificationGoalEvent event) {
		logger.debug("Received ({}, {}): id = {}", event.getClass().getSimpleName(), event.getCategory(), event.getId());
	}

	@Subscribe
	public void event(final UsesEvent event) {
		logger.debug("Received ({}, {}): role = {}, pmf = {}", event.getClass().getSimpleName(), event.getCategory(), event.getRoleId(), event.getPmfId());
	}

}
