package com.cadence.performance_platform.dto

import java.util.UUID

data class UserRoleResponse(
    val userRoleId: UUID,
    val userId: UUID,
    val roleId: UUID,
    val scopeType: String,
    val scopeTargetId: UUID?
)
