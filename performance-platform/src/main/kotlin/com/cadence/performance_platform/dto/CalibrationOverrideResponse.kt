package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class CalibrationOverrideResponse(
    val appraisalId: UUID,
    val originalScore: Double,
    val calibratedScore: Double,
    val alteredBy: UUID,
    val justification: String,
    val adjustedAt: OffsetDateTime
)
