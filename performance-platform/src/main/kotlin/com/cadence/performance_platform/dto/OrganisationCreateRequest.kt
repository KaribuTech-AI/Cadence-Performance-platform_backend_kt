package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class OrganisationCreateRequest(
    @field:NotBlank(message = "Organisation name is required")
    @JsonProperty("name") val name: String,

    @field:NotBlank(message = "Organisation code is required")
    @JsonProperty("code") val code: String
)