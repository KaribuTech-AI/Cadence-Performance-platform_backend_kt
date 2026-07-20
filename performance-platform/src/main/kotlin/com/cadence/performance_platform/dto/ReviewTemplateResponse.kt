package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class ReviewTemplateResponse(
    val templateId: UUID,
    val name: String,
    val description: String?,
    val sections: List<TemplateSectionResponse> = emptyList(),
    val isPublished: Boolean,
    val createdAt: OffsetDateTime
)
