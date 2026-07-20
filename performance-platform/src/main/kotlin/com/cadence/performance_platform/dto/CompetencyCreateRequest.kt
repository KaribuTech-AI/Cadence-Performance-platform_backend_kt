package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class CompetencyCreateRequest(
    @field:NotBlank(message = "Competency name is required")
    @JsonProperty("name") val name: String,

    @JsonProperty("description") val description: String?,
    @JsonProperty("category") val category: String,
    @JsonProperty("behaviors") val behaviors: List<BehaviorCreateRequest> = emptyList()
)
