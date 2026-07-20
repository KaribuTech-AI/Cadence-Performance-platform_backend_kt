package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class ReviewAppraisalService {

    fun getAppraisalsForManager(managerId: UUID, page: Int, size: Int): PagedReviewAppraisalResponse {
        val mockAppraisal = ReviewAppraisalResponse(
            appraisalId = UUID.randomUUID(),
            cycleId = UUID.randomUUID(),
            employeeId = UUID.randomUUID(),
            managerId = managerId,
            status = "MANAGER_REVIEW_PENDING",
            selfScore = 4.2,
            managerScore = null,
            finalScore = null,
            sections = listOf(
                AppraisalSectionResponse(UUID.randomUUID(), "Technical Execution", "Completed core engineering benchmarks ahead of cycle timelines.", null, 4.5)
            ),
            submittedAt = OffsetDateTime.now().minusDays(2),
            completedAt = null
        )
        return PagedReviewAppraisalResponse(
            content = listOf(mockAppraisal),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun submitSelfAppraisal(appraisalId: UUID, request: AppraisalSubmitRequest): ReviewAppraisalResponse {
        return ReviewAppraisalResponse(
            appraisalId = appraisalId,
            cycleId = UUID.randomUUID(),
            employeeId = UUID.randomUUID(),
            managerId = UUID.randomUUID(),
            status = "MANAGER_REVIEW_PENDING",
            selfScore = 4.0,
            managerScore = null,
            finalScore = null,
            submittedAt = OffsetDateTime.now(),
            completedAt = null
        )
    }

    fun submitManagerEvaluation(appraisalId: UUID, request: AppraisalSubmitRequest): ReviewAppraisalResponse {
        return ReviewAppraisalResponse(
            appraisalId = appraisalId,
            cycleId = UUID.randomUUID(),
            employeeId = UUID.randomUUID(),
            managerId = UUID.randomUUID(),
            status = "CALIBRATION",
            selfScore = 4.0,
            managerScore = 4.5,
            finalScore = null,
            submittedAt = OffsetDateTime.now().minusDays(1),
            completedAt = null
        )
    }

    fun executeSignOffWorkflow(appraisalId: UUID, role: String, request: AppraisalSignOffRequest): ReviewAppraisalResponse {
        return ReviewAppraisalResponse(
            appraisalId = appraisalId,
            cycleId = UUID.randomUUID(),
            employeeId = UUID.randomUUID(),
            managerId = UUID.randomUUID(),
            status = "COMPLETED",
            selfScore = 4.0,
            managerScore = 4.5,
            finalScore = 4.25,
            submittedAt = OffsetDateTime.now().minusDays(5),
            completedAt = OffsetDateTime.now()
        )
    }
}
