package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class KeyResultUpdateRequest(
    @field:NotNull(message = "Current metric value is required")
    @JsonProperty("currentValue") val currentValue: Double,

    @JsonProperty("title") val title: String?
)
