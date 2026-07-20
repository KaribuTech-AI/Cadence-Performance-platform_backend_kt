package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class FeedbackSubmissionResponse(
    val submissionId: UUID,
    val requestId: UUID,
    val status: String,
    val submittedAt: OffsetDateTime
)
