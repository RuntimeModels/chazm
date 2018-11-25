package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.UniqueId

import javax.inject.Inject

internal open class AgentEntity @Inject constructor(
    @Assisted id: UniqueId<Agent>,
    @param:Assisted private val contactInfo: Agent.ContactInfo
) : AbstractEntity<Agent>(id), Agent {

    override fun equals(other: Any?): Boolean {
        if (other is Agent) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

    override fun getContactInfo(): Agent.ContactInfo = contactInfo
}
