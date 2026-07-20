package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

@Service
class CheckinService {

    fun listCheckins(page: Int, size: Int): PagedCheckinResponse {
        val mockCheckin = CheckinResponse(
            checkinId = UUID.randomUUID(),
            managerId = UUID.randomUUID(),
            employeeId = UUID.randomUUID(),
            scheduledAt = OffsetDateTime.now().plusDays(2),
            status = "SCHEDULED",
            agenda = "Bi-weekly sync concerning engineering platform goals."
        )
        return PagedCheckinResponse(
            content = listOf(mockCheckin),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun scheduleCheckin(request: CheckinCreateRequest): CheckinResponse {
        return CheckinResponse(
            checkinId = UUID.randomUUID(),
            managerId = request.managerId,
            employeeId = request.employeeId,
            scheduledAt = request.scheduledAt,
            status = "SCHEDULED",
            agenda = request.agenda
        )
    }

    fun getCheckinDetails(checkinId: UUID): CheckinDetailResponse {
        return CheckinDetailResponse(
            checkinId = checkinId,
            managerId = UUID.randomUUID(),
            employeeId = UUID.randomUUID(),
            scheduledAt = OffsetDateTime.now().minusHours(1),
            status = "COMPLETED",
            agenda = "Sprint performance review alignment.",
            notes = "Discussed backend optimization. Core targets met for this sprint.",
            actionItems = listOf(
                ActionItemResponse(UUID.randomUUID(), "Refactor JPA entities for performance platform optimization.", LocalDate.now().plusWeeks(1), false)
            )
        )
    }

    fun updateCheckin(checkinId: UUID, request: CheckinUpdateRequest): CheckinResponse {
        return CheckinResponse(
            checkinId = checkinId,
            managerId = UUID.randomUUID(),
            employeeId = UUID.randomUUID(),
            scheduledAt = OffsetDateTime.now(),
            status = request.status ?: "COMPLETED",
            agenda = "Discuss project milestones."
        )
    }

    fun addActionItem(checkinId: UUID, request: ActionItemCreateRequest): ActionItemResponse {
        return ActionItemResponse(
            actionItemId = UUID.randomUUID(),
            description = request.description,
            dueDate = request.dueDate,
            isCompleted = false
        )
    }
}