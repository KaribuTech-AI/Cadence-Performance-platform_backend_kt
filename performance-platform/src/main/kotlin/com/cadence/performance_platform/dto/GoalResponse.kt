package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class GoalResponse(
    val goalId: UUID,
    val userId: UUID,
    val title: String,
    val description: String?,
    val type: String, // e.g., INDIVIDUAL, TEAM, COMPANY
    val status: String, // DRAFT, PENDING_APPROVAL, APPROVED, REJECTED
    val progress: Double, // Percentage 0.0 - 100.0
    val keyResults: List<KeyResultResponse> = emptyList(),
    val createdAt: OffsetDateTime
)
