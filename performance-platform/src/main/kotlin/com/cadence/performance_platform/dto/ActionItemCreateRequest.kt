package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class ActionItemCreateRequest(
    @field:NotBlank(message = "Action item description cannot be blank")
    @JsonProperty("description") val description: String,
    @JsonProperty("dueDate") val dueDate: java.time.LocalDate?
)
