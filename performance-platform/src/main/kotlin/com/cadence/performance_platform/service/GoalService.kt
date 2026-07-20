package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class GoalService {

    fun getUserGoals(userId: UUID, page: Int, size: Int): PagedGoalResponse {
        val mockGoal = GoalResponse(
            goalId = UUID.randomUUID(),
            userId = userId,
            title = "Optimize Platform Core Throughput",
            description = "Reduce API latency characteristics across high volume targets",
            type = "INDIVIDUAL",
            status = "APPROVED",
            progress = 45.0,
            keyResults = listOf(
                KeyResultResponse(UUID.randomUUID(), "Reduce DB connection leaks", 0.0, 100.0, 45.0, "PERCENTAGE")
            ),
            createdAt = OffsetDateTime.now()
        )
        return PagedGoalResponse(
            content = listOf(mockGoal),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun createGoal(userId: UUID, request: GoalCreateRequest): GoalResponse {
        val krs = request.keyResults.map {
            KeyResultResponse(UUID.randomUUID(), it.title, it.initialValue, it.targetValue, it.initialValue, it.unit)
        }
        return GoalResponse(
            goalId = UUID.randomUUID(),
            userId = userId,
            title = request.title,
            description = request.description,
            type = request.type,
            status = "DRAFT",
            progress = 0.0,
            keyResults = krs,
            createdAt = OffsetDateTime.now()
        )
    }

    fun updateKeyResultProgress(goalId: UUID, krId: UUID, request: GoalProgressUpdateRequest): GoalResponse {
        return GoalResponse(
            goalId = goalId,
            userId = UUID.randomUUID(),
            title = "Updated Objective Matrix",
            description = null,
            type = "INDIVIDUAL",
            status = "APPROVED",
            progress = 75.0,
            keyResults = listOf(
                KeyResultResponse(krId, "Metric Evaluation Indicator", 0.0, 100.0, request.currentValue, "PERCENTAGE")
            ),
            createdAt = OffsetDateTime.now()
        )
    }

    fun submitGoalForApproval(goalId: UUID): GoalResponse {
        return GoalResponse(
            goalId = goalId,
            userId = UUID.randomUUID(),
            title = "Pending Platform Objective",
            description = null,
            type = "INDIVIDUAL",
            status = "PENDING_APPROVAL",
            progress = 0.0,
            createdAt = OffsetDateTime.now()
        )
    }

    fun handleGoalApprovalWorkflow(goalId: UUID, request: GoalApprovalRequest): GoalResponse {
        val finalStatus = if (request.action.uppercase() == "APPROVE") "APPROVED" else "REJECTED"
        return GoalResponse(
            goalId = goalId,
            userId = UUID.randomUUID(),
            title = "Evaluated Platform Objective",
            description = null,
            type = "INDIVIDUAL",
            status = finalStatus,
            progress = 0.0,
            createdAt = OffsetDateTime.now()
        )
    }
}
