package runtimemodels.chazm.model.entity.impl

import mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.Mockito.`when`
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.AgentId

internal class DefaultAgentManagerTest {

    @Test
    fun `add works properly`() {
        val agent: Agent = mock()
        val agentId: AgentId = mock()

        val manager = DefaultAgentManager(mutableMapOf())

        `when`(agent.id).thenReturn(agentId)

        manager.add(agent)

        assertAll(
            { assertThat(manager.keys).isNotEmpty },
            { assertThat(manager.keys).containsOnly(agentId) },
            { assertThat(manager.size).isEqualTo(1) },
            { assertThat(manager[agentId]).isSameAs(agent) }
        )
    }

    @Test
    fun `remove return null when agent does not exists`() {
        val agentId: AgentId = mock()

        val manager = DefaultAgentManager(mutableMapOf())

        assertAll(
            { assertThat(manager[agentId]).isNull() },
            { assertThat(manager.remove(agentId)).isNull() }
        )
    }

    @Test
    fun `remove properly removes the agent`() {
        val agent: Agent = mock()
        val agentId: AgentId = mock()

        val manager = DefaultAgentManager(mutableMapOf())

        `when`(agent.id).thenReturn(agentId)

        manager.add(agent)

        assertAll(
            { assertThat(manager[agentId]).isSameAs(agent) },
            { assertThat(manager.remove(agentId)).isSameAs(agent) },
            { assertThat(manager[agentId]).isNull() }
        )
    }
}
