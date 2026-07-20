package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class WorkflowTaskResponse(
    val taskId: UUID,
    val title: String,
    val type: String, // e.g., "PERFORMANCE_REVIEW_SIGN_OFF", "GOAL_APPROVAL"
    val status: String, // e.g., "PENDING", "COMPLETED", "REASSIGNED"
    val assigneeId: UUID,
    val dueDate: OffsetDateTime?
)
