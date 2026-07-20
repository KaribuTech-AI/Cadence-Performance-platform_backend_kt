package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class OrganisationUnitCreateRequest(
    @JsonProperty("parentUnitId") val parentUnitId: java.util.UUID?,

    @field:NotBlank(message = "Unit name is required")
    @JsonProperty("name") val name: String,

    @field:NotBlank(message = "Unit code is required")
    @JsonProperty("code") val code: String,

    @field:NotBlank(message = "Unit type is required")
    @JsonProperty("type") val type: String
)