package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class UserRoleAssignmentRequest(
    @JsonProperty("roleId") val roleId: UUID,
    @JsonProperty("scopeType") val scopeType: String, // e.g., GLOBAL, ORGANISATION, UNIT
    @JsonProperty("scopeTargetId") val scopeTargetId: UUID?
)
