package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class TemplateSectionCreateRequest(
    @field:NotBlank(message = "Section title is required")
    @JsonProperty("title") val title: String,

    @JsonProperty("type") val type: String,
    @JsonProperty("weight") val weight: Double
)
