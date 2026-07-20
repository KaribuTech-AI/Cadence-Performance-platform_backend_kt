package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class ConfigurationUpdateRequest(
    @field:NotBlank(message = "Value cannot be blank")
    @JsonProperty("configValue") val configValue: String
)
