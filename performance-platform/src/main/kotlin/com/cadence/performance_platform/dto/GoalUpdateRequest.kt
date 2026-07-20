package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class GoalUpdateRequest(
    @JsonProperty("title") val title: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("targetDate") val targetDate: LocalDate?,
    @JsonProperty("status") val status: String?
)
