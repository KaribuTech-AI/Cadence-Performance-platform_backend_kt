package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class AppraisalSignOffRequest(
    @field:NotBlank(message = "Signature token or statement is required")
    @JsonProperty("signatureStatement") val signatureStatement: String
)
