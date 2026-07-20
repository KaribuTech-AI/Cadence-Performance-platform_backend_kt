package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class GoalCreateRequest(
    @field:NotBlank(message = "Goal title is required")
    @JsonProperty("title") val title: String,

    @JsonProperty("description") val description: String?,
    @JsonProperty("type") val type: String,
    @JsonProperty("keyResults") val keyResults: List<KeyResultCreateRequest> = emptyList()
)
