package model.organization.event;

import model.organization.entity.Agent;

/**
 * The {@linkplain EventFactory} interface defines the APIs for constructing events.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface EventFactory {

	/**
	 * @param agent
	 * @param category
	 * @return
	 */
	AgentEvent build(Agent agent, UpdateCategory category);

}
