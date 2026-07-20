package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class ReviewCycleService {

    fun getReviewCycles(page: Int, size: Int): PagedReviewCycleResponse {
        val mockCycle = ReviewCycleResponse(
            cycleId = UUID.randomUUID(),
            name = "2026 Annual Performance Review",
            description = "Company-wide end of year evaluations",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now().plusMonths(3),
            status = "DRAFT",
            createdAt = OffsetDateTime.now()
        )
        return PagedReviewCycleResponse(
            content = listOf(mockCycle),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun createReviewCycle(request: ReviewCycleCreateRequest): ReviewCycleResponse {
        return ReviewCycleResponse(
            cycleId = UUID.randomUUID(),
            name = request.name,
            description = request.description,
            startDate = request.startDate,
            endDate = request.endDate,
            status = "DRAFT",
            createdAt = OffsetDateTime.now()
        )
    }

    fun updateReviewCycle(cycleId: UUID, request: ReviewCycleUpdateRequest): ReviewCycleResponse {
        return ReviewCycleResponse(
            cycleId = cycleId,
            name = request.name ?: "Updated Review Cycle",
            description = request.description,
            startDate = request.startDate ?: OffsetDateTime.now(),
            endDate = request.endDate ?: OffsetDateTime.now().plusMonths(3),
            status = "DRAFT",
            createdAt = OffsetDateTime.now()
        )
    }

    fun publishReviewCycle(cycleId: UUID): ReviewCycleResponse {
        return ReviewCycleResponse(
            cycleId = cycleId,
            name = "Published Review Cycle",
            description = null,
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now().plusMonths(3),
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
    }
}
