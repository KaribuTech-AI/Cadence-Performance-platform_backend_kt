package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class UserStatusRequest(
    @field:NotBlank(message = "Status is required")
    @JsonProperty("status") val status: String // ACTIVE, INACTIVE, ARCHIVED
)
