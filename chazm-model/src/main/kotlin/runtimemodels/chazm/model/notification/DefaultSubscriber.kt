package runtimemodels.chazm.model.notification

import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.message.L
import runtimemodels.chazm.model.relation.*

internal open class DefaultSubscriber : Subscriber {

    @Subscribe
    fun event(event: AchievesEvent) {
        log.debug(L.RECEIVED_ROLE_GOAL.get(), event.javaClass.simpleName, event.category, event.roleId, event.goalId)
    }

    @Subscribe
    fun event(event: AgentEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: AssignmentEvent) {
        log.debug(L.RECEIVED_AGENT_ROLE_GOAL.get(), event.javaClass.simpleName, event.category, event.agentId, event.roleId,
            event.goalId)
    }

    @Subscribe
    fun event(event: AttributeEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: CapabilityEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: CharacteristicEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: ContainsEvent) {
        log.debug(L.RECEIVED_ROLE_CHARACTERISTIC_VALUE.get(), event.javaClass.simpleName, event.category, event.roleId,
            event.characteristicId, event.value)
    }

    @Subscribe
    fun event(event: HasEvent) {
        log.debug(L.RECEIVED_AGENT_ATTRIBUTE_VALUE.get(), event.javaClass.simpleName, event.category, event.agentId, event.attributeId,
            event.value)
    }

    @Subscribe
    fun event(event: InstanceGoalEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: ModeratesEvent) {
        log.debug(L.RECEIVED_PMF_ATTRIBUTE.get(), event.javaClass.simpleName, event.category, event.pmfId, event.attributeId)
    }

    @Subscribe
    fun event(event: NeedsEvent) {
        log.debug(L.RECEIVED_ROLE_ATTRIBUTE.get(), event.javaClass.simpleName, event.category, event.roleId, event.attributeId)
    }

    @Subscribe
    fun event(event: PmfEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: PolicyEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: PossessesEvent) {
        log.debug(L.RECEIVED_AGENT_CAPABILITY_SCORE.get(), event.javaClass.simpleName, event.category, event.agentId,
            event.capabilityId, event.score)
    }

    @Subscribe
    fun event(event: RequiresEvent) {
        log.debug(L.RECEIVED_ROLE_CAPABILITY.get(), event.javaClass.simpleName, event.category, event.roleId, event.capabilityId)
    }

    @Subscribe
    fun event(event: RoleEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: SpecificationGoalEvent) {
        log.debug(L.RECEIVED_ID.get(), event.javaClass.simpleName, event.category, event.id)
    }

    @Subscribe
    fun event(event: UsesEvent) {
        log.debug(L.RECEIVED_ROLE_PMF.get(), event.javaClass.simpleName, event.category, event.roleId, event.pmfId)
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(DefaultSubscriber::class.java)
    }

}
