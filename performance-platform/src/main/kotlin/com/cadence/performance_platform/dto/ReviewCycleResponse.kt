package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class ReviewCycleResponse(
    val cycleId: UUID,
    val name: String,
    val description: String?,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val status: String, // DRAFT, ACTIVE, COMPLETED, ARCHIVED
    val createdAt: OffsetDateTime
)

