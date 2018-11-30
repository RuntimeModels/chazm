package runtimemodels.chazm.model

import com.google.inject.AbstractModule
import com.google.inject.Provides
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.organization.AgentManager
import runtimemodels.chazm.api.organization.AttributeManager
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.event.EventModule
import runtimemodels.chazm.model.function.FunctionModule
import runtimemodels.chazm.model.notification.NotificationModule
import runtimemodels.chazm.model.organizations.DefaultAgentManager
import runtimemodels.chazm.model.organizations.DefaultAttributeManager
import runtimemodels.chazm.model.organizations.DefaultOrganization
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

    override fun configure() {
        bind(Organization::class.java).to(DefaultOrganization::class.java)
        bind(AgentManager::class.java).to(DefaultAgentManager::class.java)
        bind(AttributeManager::class.java).to(DefaultAttributeManager::class.java)
        install(RelationModule())
        install(FunctionModule())
        install(EventModule())
        install(NotificationModule())
    }
}
