package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty
import java.util.UUID

data class CalibrationSessionCreateRequest(
    @JsonProperty("cycleId") val cycleId: UUID,
    @JsonProperty("targetDepartmentId") val targetDepartmentId: UUID?,

    @field:NotEmpty(message = "At least one moderator ID must be assigned")
    @JsonProperty("moderators") val moderators: List<UUID>
)
