package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class CalibrationSessionResponse(
    val sessionId: UUID,
    val cycleId: UUID,
    val targetDepartmentId: UUID?,
    val status: String, // e.g., PENDING, IN_PROGRESS, CONCLUDED
    val totalAppraisalsReviewed: Int,
    val moderators: List<UUID> = emptyList(),
    val createdAt: OffsetDateTime
)
