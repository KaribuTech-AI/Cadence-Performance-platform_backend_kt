package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class TaskReassignmentRequest(
    @field:NotNull(message = "New assignee ID is required")
    @JsonProperty("assigneeId") val assigneeId: UUID,

    @JsonProperty("reason") val reason: String?
)
