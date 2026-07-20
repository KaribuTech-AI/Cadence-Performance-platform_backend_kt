package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class ReviewCycleUpdateRequest(
    @JsonProperty("name") val name: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("startDate") val startDate: OffsetDateTime?,
    @JsonProperty("endDate") val endDate: OffsetDateTime?
)
