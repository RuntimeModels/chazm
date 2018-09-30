package runtimemodels.chazm.model.event

data class OrganizationEvent<T>(
        val type: EventType,
        val o : T
)