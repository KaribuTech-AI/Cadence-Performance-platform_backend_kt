package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class WorkflowTaskService {

    fun listTasks(page: Int, size: Int): PagedWorkflowTaskResponse {
        val mockTask = WorkflowTaskResponse(
            taskId = UUID.randomUUID(),
            title = "Sign off Q2 Review for Engineering Pod",
            type = "PERFORMANCE_REVIEW_SIGN_OFF",
            status = "PENDING",
            assigneeId = UUID.randomUUID(),
            dueDate = OffsetDateTime.now().plusDays(3)
        )
        return PagedWorkflowTaskResponse(listOf(mockTask), 1, 1, page, size)
    }

    fun getTaskDetails(taskId: UUID): WorkflowTaskDetailResponse {
        val mockHistory = TaskHistoryLogResponse(
            logId = UUID.randomUUID(),
            action = "CREATED",
            actorId = UUID.randomUUID(),
            notes = "Automated engine orchestration initialization.",
            timestamp = OffsetDateTime.now().minusDays(1)
        )
        return WorkflowTaskDetailResponse(
            taskId = taskId,
            title = "Sign off Q2 Review for Engineering Pod",
            description = "Final executive confirmation of cross-functional team calibrations.",
            type = "PERFORMANCE_REVIEW_SIGN_OFF",
            status = "PENDING",
            assigneeId = UUID.randomUUID(),
            createdBy = UUID.randomUUID(),
            dueDate = OffsetDateTime.now().plusDays(3),
            createdAt = OffsetDateTime.now().minusDays(1),
            auditHistory = listOf(mockHistory)
        )
    }

    fun completeTask(taskId: UUID, request: TaskCompletionRequest): WorkflowTaskResponse {
        return WorkflowTaskResponse(
            taskId = taskId,
            title = "Sign off Q2 Review for Engineering Pod",
            type = "PERFORMANCE_REVIEW_SIGN_OFF",
            status = request.outcome.uppercase(),
            assigneeId = UUID.randomUUID(),
            dueDate = null
        )
    }

    fun reassignTask(taskId: UUID, request: TaskReassignmentRequest): WorkflowTaskResponse {
        return WorkflowTaskResponse(
            taskId = taskId,
            title = "Sign off Q2 Review for Engineering Pod",
            type = "PERFORMANCE_REVIEW_SIGN_OFF",
            status = "REASSIGNED",
            assigneeId = request.assigneeId,
            dueDate = OffsetDateTime.now().plusDays(5)
        )
    }
}
