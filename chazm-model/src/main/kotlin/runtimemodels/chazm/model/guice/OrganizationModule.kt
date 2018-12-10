package runtimemodels.chazm.model.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.*
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.entity.impl.*
import runtimemodels.chazm.model.organization.DefaultOrganization
import runtimemodels.chazm.model.relation.impl.*

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
    fun instanceGoalsBy(): MutableMap<SpecificationGoalId, MutableMap<InstanceGoalId, InstanceGoal>> = mutableMapOf()

    @Provides
    fun pmfs(): MutableMap<PmfId, Pmf> = mutableMapOf()

    @Provides
    fun policies(): MutableMap<PolicyId, Policy> = mutableMapOf()

    @Provides
    fun roles(): MutableMap<RoleId, Role> = mutableMapOf()

    @Provides
    fun specificationGoals(): MutableMap<SpecificationGoalId, SpecificationGoal> = mutableMapOf()

    @Provides
    fun achieves(): MutableMap<RoleId, MutableMap<SpecificationGoalId, Achieves>> = mutableMapOf()

    @Provides
    fun achievesBy(): MutableMap<SpecificationGoalId, MutableMap<RoleId, Achieves>> = mutableMapOf()

    @Provides
    fun assignments(): MutableMap<AgentId, MutableMap<RoleId, MutableMap<InstanceGoalId, Assignment>>> = mutableMapOf()

    @Provides
    fun assignmentsByRole(): MutableMap<RoleId, MutableMap<AgentId, MutableMap<InstanceGoalId, Assignment>>> = mutableMapOf()

    @Provides
    fun assignmentsByGoal(): MutableMap<InstanceGoalId, MutableMap<AgentId, MutableMap<RoleId, Assignment>>> = mutableMapOf()

    @Provides
    fun contains(): MutableMap<RoleId, MutableMap<CharacteristicId, Contains>> = mutableMapOf()

    @Provides
    fun containsBy(): MutableMap<CharacteristicId, MutableMap<RoleId, Contains>> = mutableMapOf()

    @Provides
    fun has(): MutableMap<AgentId, MutableMap<AttributeId, Has>> = mutableMapOf()

    @Provides
    fun hasBy(): MutableMap<AttributeId, MutableMap<AgentId, Has>> = mutableMapOf()

    @Provides
    fun moderates(): MutableMap<PmfId, MutableMap<AttributeId, Moderates>> = mutableMapOf()

    @Provides
    fun moderatesBy(): MutableMap<AttributeId, MutableMap<PmfId, Moderates>> = mutableMapOf()

    @Provides
    fun needs(): MutableMap<RoleId, MutableMap<AttributeId, Needs>> = mutableMapOf()

    @Provides
    fun needsBy(): MutableMap<AttributeId, MutableMap<RoleId, Needs>> = mutableMapOf()

    @Provides
    fun possesses(): MutableMap<AgentId, MutableMap<CapabilityId, Possesses>> = mutableMapOf()

    @Provides
    fun possessesBy(): MutableMap<CapabilityId, MutableMap<AgentId, Possesses>> = mutableMapOf()

    @Provides
    fun requires(): MutableMap<RoleId, MutableMap<CapabilityId, Requires>> = mutableMapOf()

    @Provides
    fun requiresBy(): MutableMap<CapabilityId, MutableMap<RoleId, Requires>> = mutableMapOf()

    @Provides
    fun uses(): MutableMap<RoleId, MutableMap<PmfId, Uses>> = mutableMapOf()

    @Provides
    fun usesBy(): MutableMap<PmfId, MutableMap<RoleId, Uses>> = mutableMapOf()

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
        bind(SpecificationGoalManager::class.java).to(DefaultSpecifcationGoalManager::class.java)

        bind(AchievesManager::class.java).to(DefaultAchievesManager::class.java)
        bind(AssignmentManager::class.java).to(DefaultAssignmentManager::class.java)
        bind(ContainsManager::class.java).to(DefaultContainsManager::class.java)
        bind(HasManager::class.java).to(DefaultHasManager::class.java)
        bind(ModeratesManager::class.java).to(DefaultModeratesManager::class.java)
        bind(NeedsManager::class.java).to(DefaultNeedsManager::class.java)
        bind(PossessesManager::class.java).to(DefaultPossessesManager::class.java)
        bind(RequiresManager::class.java).to(DefaultRequiresManager::class.java)
        bind(UsesManager::class.java).to(DefaultUsesManager::class.java)

        install(RelationModule())
        install(FunctionModule())
        install(EventModule())
        install(NotificationModule())
    }
}
