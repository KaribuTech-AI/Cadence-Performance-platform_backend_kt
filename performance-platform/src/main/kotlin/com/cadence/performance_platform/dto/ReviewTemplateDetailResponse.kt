package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class ReviewTemplateDetailResponse(
    val templateId: UUID,
    val name: String,
    val description: String?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime,
    val components: List<TemplateComponentResponse>
)

data class TemplateComponentResponse(
    val componentId: UUID,
    val title: String,
    val weight: Double,
    val competencies: List<CompetencySummaryResponse>
)

data class CompetencySummaryResponse(
    val competencyId: UUID,
    val name: String,
    val code: String // e.g., "TECH_LEADERSHIP", "CODE_QUALITY"
)