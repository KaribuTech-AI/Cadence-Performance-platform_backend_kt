package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class RoleCreateRequest(
    @field:NotBlank(message = "Role name is required")
    @JsonProperty("name") val name: String,

    @JsonProperty("description") val description: String?
)
