package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class CompetencyResponse(
    val competencyId: UUID,
    val name: String,
    val description: String?,
    val category: String, // e.g., CORE, TECHNICAL, LEADERSHIP
    val behaviors: List<BehaviorResponse> = emptyList(),
    val createdAt: OffsetDateTime
)
