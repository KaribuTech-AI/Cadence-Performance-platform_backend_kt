package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class UserUpdateRequest(
    @JsonProperty("firstName") val firstName: String?,
    @JsonProperty("lastName") val lastName: String?,
    @JsonProperty("positionId") val positionId: UUID?,
    @JsonProperty("unitId") val unitId: UUID?
)
