package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.model.message.E

class AssignmentExistsException(
    agentId: AgentId,
    roleId: RoleId,
    goalId: InstanceGoalId
) : IllegalArgumentException(E.RELATION_ALREADY_EXISTS[Assignment::class, agentId, roleId, goalId])
