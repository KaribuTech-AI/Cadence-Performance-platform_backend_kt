package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class UserResponse(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val positionId: UUID?,
    val unitId: UUID?,
    val managerId: UUID?,
    val status: String,
    val createdAt: OffsetDateTime
)
