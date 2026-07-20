package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class FeedbackResponse(
    val feedbackId: UUID,
    val senderId: UUID,
    val receiverId: UUID,
    val message: String,
    val visibility: String, // e.g., PUBLIC, PRIVATE_TO_RECEIVER, MANAGER_ONLY
    val badgeType: String?,  // e.g., INNOVATION, TEAMWORK, COLLABORATION, null for regular feedback
    val createdAt: OffsetDateTime
)
