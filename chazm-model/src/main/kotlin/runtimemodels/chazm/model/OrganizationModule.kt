package runtimemodels.chazm.model

import com.google.inject.AbstractModule
import com.google.inject.Provides
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.*
import runtimemodels.chazm.model.event.EventModule
import runtimemodels.chazm.model.function.FunctionModule
import runtimemodels.chazm.model.notification.NotificationModule
import runtimemodels.chazm.model.organizations.*
import runtimemodels.chazm.model.relation.RelationModule

/**
 * The [OrganizationModule] class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class OrganizationModule : AbstractModule() {
    @Provides
    fun agents(): MutableMap<AgentId, Agent> = mutableMapOf()

    @Provides
    fun attributes(): MutableMap<AttributeId, Attribute> = mutableMapOf()

    @Provides
    fun capabilities(): MutableMap<CapabilityId, Capability> = mutableMapOf()

    @Provides
    fun characteristics(): MutableMap<CharacteristicId, Characteristic> = mutableMapOf()

    @Provides
    fun instanceGoals(): MutableMap<InstanceGoalId, InstanceGoal> = mutableMapOf()

    @Provides
    fun instanceGoalsBySpecificationGoals(): MutableMap<SpecificationGoalId, MutableMap<InstanceGoalId, InstanceGoal>> = mutableMapOf()

    @Provides
    fun pmfs(): MutableMap<PmfId, Pmf> = mutableMapOf()

    @Provides
    fun policies(): MutableMap<PolicyId, Policy> = mutableMapOf()

    @Provides
    fun roles(): MutableMap<RoleId, Role> = mutableMapOf()

    override fun configure() {
        bind(Organization::class.java).to(DefaultOrganization::class.java)
        bind(AgentManager::class.java).to(DefaultAgentManager::class.java)
        bind(AttributeManager::class.java).to(DefaultAttributeManager::class.java)
        bind(CapabilityManager::class.java).to(DefaultCapabilityManager::class.java)
        bind(CharacteristicManager::class.java).to(DefaultCharacteristicManager::class.java)
        bind(InstanceGoalManager::class.java).to(DefaultInstanceGoalManager::class.java)
        bind(PmfManager::class.java).to(DefaultPmfManager::class.java)
        bind(PolicyManager::class.java).to(DefaultPolicyManager::class.java)
        bind(RoleManager::class.java).to(DefaultRoleManager::class.java)

        install(RelationModule())
        install(FunctionModule())
        install(EventModule())
        install(NotificationModule())
    }
}
