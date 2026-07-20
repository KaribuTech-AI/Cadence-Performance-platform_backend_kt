package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty

data class AIInsightGenerationRequest(
    @field:NotEmpty(message = "Source cycle IDs cannot be empty")
    @JsonProperty("cycleIds") val cycleIds: List<java.util.UUID>,
    @JsonProperty("focusAreas") val focusAreas: List<String>? // e.g., ["TECHNICAL_SKILLS", "COMMUNICATION"]
)
