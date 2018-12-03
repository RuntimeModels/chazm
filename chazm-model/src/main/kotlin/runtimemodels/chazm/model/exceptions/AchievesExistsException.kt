package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.model.message.E

class AchievesExistsException(
    roleId: RoleId,
    goalId: SpecificationGoalId
) : IllegalArgumentException(E.RELATION_ALREADY_EXISTS[Achieves::class, roleId, goalId])