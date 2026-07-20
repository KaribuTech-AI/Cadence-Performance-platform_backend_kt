package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class TaskCompletionRequest(
    @field:NotBlank(message = "Outcome status is required (e.g., APPROVED, REJECTED, COMPLETED)")
    @JsonProperty("outcome") val outcome: String,

    @JsonProperty("notes") val notes: String?
)
