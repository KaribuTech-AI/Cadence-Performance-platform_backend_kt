package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class BehaviorCreateRequest(
    @field:NotBlank(message = "Behavior description is required")
    @JsonProperty("description") val description: String,

    @field:Min(value = 1, message = "Expected level must be at least 1")
    @JsonProperty("expectedLevel") val expectedLevel: Int
)
