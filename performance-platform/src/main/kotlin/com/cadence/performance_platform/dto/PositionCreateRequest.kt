package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class PositionCreateRequest(
    @field:NotBlank(message = "Position title is required")
    @JsonProperty("title") val title: String,

    @field:NotBlank(message = "Position code is required")
    @JsonProperty("code") val code: String,

    @JsonProperty("jobGradeId") val jobGradeId: UUID?
)
