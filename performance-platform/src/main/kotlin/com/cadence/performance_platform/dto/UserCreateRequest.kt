package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class UserCreateRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    @JsonProperty("email") val email: String,

    @field:NotBlank(message = "First name is required")
    @JsonProperty("firstName") val firstName: String,

    @field:NotBlank(message = "Last name is required")
    @JsonProperty("lastName") val lastName: String,

    @JsonProperty("positionId") val positionId: UUID?,
    @JsonProperty("unitId") val unitId: UUID?,
    @JsonProperty("managerId") val managerId: UUID?
)
