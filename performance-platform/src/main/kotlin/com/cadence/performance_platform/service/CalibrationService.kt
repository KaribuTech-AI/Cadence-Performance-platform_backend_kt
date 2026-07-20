package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class CalibrationService {

    fun createSession(request: CalibrationSessionCreateRequest): CalibrationSessionResponse {
        return CalibrationSessionResponse(
            sessionId = UUID.randomUUID(),
            cycleId = request.cycleId,
            targetDepartmentId = request.targetDepartmentId,
            status = "PENDING",
            totalAppraisalsReviewed = 0,
            moderators = request.moderators,
            createdAt = OffsetDateTime.now()
        )
    }

    fun applyScoreOverride(
        sessionId: UUID,
        appraisalId: UUID,
        moderatorId: UUID,
        request: ScoreOverrideRequest
    ): CalibrationOverrideResponse {
        return CalibrationOverrideResponse(
            appraisalId = appraisalId,
            originalScore = 3.5,
            calibratedScore = request.calibratedScore,
            alteredBy = moderatorId,
            justification = request.justification,
            adjustedAt = OffsetDateTime.now()
        )
    }

    fun closeSession(sessionId: UUID): CalibrationSessionResponse {
        return CalibrationSessionResponse(
            sessionId = sessionId,
            cycleId = UUID.randomUUID(),
            targetDepartmentId = UUID.randomUUID(),
            status = "CONCLUDED",
            totalAppraisalsReviewed = 42,
            moderators = listOf(UUID.randomUUID()),
            createdAt = OffsetDateTime.now().minusDays(1)
        )
    }
}
