package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class AIInsightGenerationResponse(
    val trackingId: UUID,
    val status: String, // e.g., "PROCESSING", "COMPLETED"
    val estimatedDurationSeconds: Int,
    val requestedAt: OffsetDateTime
)
