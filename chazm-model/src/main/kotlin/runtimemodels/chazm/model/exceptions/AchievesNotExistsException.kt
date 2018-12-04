package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.model.message.E

class AchievesNotExistsException(
    roleId: RoleId,
    goalId: SpecificationGoalId
) : IllegalArgumentException(E.RELATION_DOES_NOT_EXISTS[Achieves::class, roleId, goalId])
