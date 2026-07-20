package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class FeedbackRequestDetailResponse(
    val requestId: UUID,
    val targetUserId: UUID,
    val requestorId: UUID,
    val templateId: UUID,
    val templateName: String,
    val status: String, // e.g., "PENDING", "SUBMITTED", "EXPIRED"
    val deadline: OffsetDateTime,
    val questions: List<FeedbackQuestionResponse>
)

data class FeedbackQuestionResponse(
    val questionId: UUID,
    val type: String, // e.g., "SCALE", "TEXT"
    val prompt: String,
    val section: String, // e.g., "LEADERSHIP", "TECHNICAL_SKILLS"
    val isRequired: Boolean
)
