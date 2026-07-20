package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class ReviewAppraisalResponse(
    val appraisalId: UUID,
    val cycleId: UUID,
    val employeeId: UUID,
    val managerId: UUID,
    val status: String, // e.g., SELF_APPRAISAL_PENDING, MANAGER_REVIEW_PENDING, CALIBRATION, COMPLETED
    val selfScore: Double?,
    val managerScore: Double?,
    val finalScore: Double?,
    val sections: List<AppraisalSectionResponse> = emptyList(),
    val submittedAt: OffsetDateTime?,
    val completedAt: OffsetDateTime?
)
