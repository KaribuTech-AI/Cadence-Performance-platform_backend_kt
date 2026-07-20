package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import java.util.UUID

data class CheckinCreateRequest(
    @field:NotNull(message = "Manager ID is required")
    @JsonProperty("managerId") val managerId: UUID,
    @field:NotNull(message = "Employee ID is required")
    @JsonProperty("employeeId") val employeeId: UUID,
    @field:NotNull(message = "Scheduled time is required")
    @JsonProperty("scheduledAt") val scheduledAt: OffsetDateTime,
    @JsonProperty("agenda") val agenda: String?
)
