package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class JobGradeCreateRequest(
    @field:NotBlank(message = "Job grade name is required")
    @JsonProperty("name") val name: String,

    @field:NotBlank(message = "Job grade code is required")
    @JsonProperty("code") val code: String,

    @field:Min(value = 1, message = "Level must be greater than or equal to 1")
    @JsonProperty("level") val level: Int
)
