package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class ManagerAssignmentRequest(
    @JsonProperty("managerId") val managerId: UUID?
)
