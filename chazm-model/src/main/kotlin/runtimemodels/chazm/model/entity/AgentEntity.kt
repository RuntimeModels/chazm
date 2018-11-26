package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.UniqueId

import javax.inject.Inject

internal open class AgentEntity @Inject constructor(
    @Assisted id: UniqueId<Agent>,
    @Assisted contactInfo: Map<Any, Any>
) : AbstractEntity<Agent>(id), Agent {
    private val contactInfo: MutableMap<Any, Any> = mutableMapOf()

    init {
        this.contactInfo.putAll(contactInfo)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Agent) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

    override fun getContactInfo(): Map<Any, Any> = contactInfo.toMap()

    override fun addContactInfo(key: Any, value: Any) {
        contactInfo[key] = value
    }

    override fun removeContactInfo(key: Any) {
        contactInfo.remove(key)
    }
}
