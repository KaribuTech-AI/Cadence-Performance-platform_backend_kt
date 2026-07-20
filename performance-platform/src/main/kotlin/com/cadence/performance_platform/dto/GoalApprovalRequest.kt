package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class GoalApprovalRequest(
    @field:NotBlank(message = "Action is required")
    @JsonProperty("action") val action: String, // APPROVE, REJECT
    @JsonProperty("comment") val comment: String?
)
