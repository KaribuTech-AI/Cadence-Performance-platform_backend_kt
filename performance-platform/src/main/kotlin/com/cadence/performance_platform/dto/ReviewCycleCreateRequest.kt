package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.time.OffsetDateTime

data class ReviewCycleCreateRequest(
    @field:NotBlank(message = "Cycle name is required")
    @JsonProperty("name") val name: String,

    @JsonProperty("description") val description: String?,
    @JsonProperty("startDate") val startDate: OffsetDateTime,
    @JsonProperty("endDate") val endDate: OffsetDateTime
)
