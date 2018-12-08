package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Assignment
import javax.inject.Inject

internal data class AssignmentRelation @Inject constructor(
    @param:Assisted override val agent: Agent,
    @param:Assisted override val role: Role,
    @param:Assisted override val goal: InstanceGoal
) : Assignment
