package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class GoalUpdateResponse(
    val updateId: UUID,
    val goalId: UUID,
    val currentProgress: Double,
    val commentary: String,
    val updatedBy: UUID,
    val updatedAt: OffsetDateTime
)