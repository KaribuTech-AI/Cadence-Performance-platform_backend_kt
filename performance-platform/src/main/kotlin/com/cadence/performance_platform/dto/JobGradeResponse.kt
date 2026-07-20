package com.cadence.performance_platform.dto

import java.util.UUID

data class JobGradeResponse(
    val jobGradeId: UUID,
    val name: String, // e.g., Grade 1, Tier A, L5
    val code: String,
    val level: Int
)
