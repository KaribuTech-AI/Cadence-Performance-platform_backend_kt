package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class WorkflowTaskDetailResponse(
    val taskId: UUID,
    val title: String,
    val description: String?,
    val type: String,
    val status: String,
    val assigneeId: UUID,
    val createdBy: UUID,
    val dueDate: OffsetDateTime?,
    val createdAt: OffsetDateTime,
    val auditHistory: List<TaskHistoryLogResponse>
)

data class TaskHistoryLogResponse(
    val logId: UUID,
    val action: String, // e.g., "CREATED", "REASSIGNED", "COMPLETED"
    val actorId: UUID,
    val notes: String?,
    val timestamp: OffsetDateTime
)
