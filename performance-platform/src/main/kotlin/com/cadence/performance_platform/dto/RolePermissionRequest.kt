package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RolePermissionRequest(
    @JsonProperty("permissions") val permissions: List<String>
)
