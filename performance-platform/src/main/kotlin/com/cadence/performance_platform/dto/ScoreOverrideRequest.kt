package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class ScoreOverrideRequest(
    @field:Min(value = 0, message = "Calibrated score cannot be less than 0.0")
    @field:Max(value = 5, message = "Calibrated score cannot exceed 5.0")
    @JsonProperty("calibratedScore") val calibratedScore: Double,

    @field:NotBlank(message = "A justification for overriding scores is mandatory")
    @JsonProperty("justification") val justification: String
)

