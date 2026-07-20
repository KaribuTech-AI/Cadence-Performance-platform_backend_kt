package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class KeyResultCreateRequest(
    @field:NotBlank(message = "Key result title is required")
    @JsonProperty("title") val title: String,

    @JsonProperty("initialValue") val initialValue: Double,
    @JsonProperty("targetValue") val targetValue: Double,
    @JsonProperty("unit") val unit: String
)
