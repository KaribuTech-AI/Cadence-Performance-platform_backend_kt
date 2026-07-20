package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class GoalDecisionRequest(
    @field:NotBlank(message = "Decision action is required (APPROVE/REJECT)")
    @JsonProperty("action") val action: String,

    @JsonProperty("comment") val comment: String?
)
