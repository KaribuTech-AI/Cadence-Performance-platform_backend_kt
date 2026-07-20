package com.cadence.performance_platform.dto

import java.util.UUID

data class RoleResponse(
    val roleId: UUID,
    val name: String,
    val description: String?,
    val permissions: List<String> = emptyList()
)
