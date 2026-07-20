package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class AIInsightResponse(
    val insightId: UUID,
    val userId: UUID,
    val insightType: String, // e.g., "PERFORMANCE_TREND", "LEADERSHIP_POTENTIAL", "BURNOUT_RISK"
    val summary: String,
    val details: String,
    val confidenceScore: Double, // e.g., 0.89
    val generatedAt: OffsetDateTime
)
