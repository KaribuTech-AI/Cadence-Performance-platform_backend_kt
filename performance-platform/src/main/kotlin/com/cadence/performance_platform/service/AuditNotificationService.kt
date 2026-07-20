package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class AuditNotificationService {

    fun getAuditTrail(actionType: String?, page: Int, size: Int): PagedAuditLogResponse {
        val mockAudit = AuditLogResponse(
            logId = UUID.randomUUID(),
            principalId = UUID.randomUUID(),
            actionType = actionType ?: "CALIBRATION_SCORE_OVERRIDE",
            targetResource = "APPRAISAL",
            resourceId = UUID.randomUUID(),
            ipAddress = "192.168.1.105",
            changeSummary = "Score manually calibrated from 3.50 to 4.25 due to engineering performance normalization.",
            timestamp = OffsetDateTime.now()
        )
        return PagedAuditLogResponse(
            content = listOf(mockAudit),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun getUserAlerts(userId: UUID): List<SystemAlertResponse> {
        return listOf(
            SystemAlertResponse(
                alertId = UUID.randomUUID(),
                targetUserId = userId,
                title = "Appraisal Cycle Published",
                briefDescription = "The 2026 Q3 Mid-Year Review cycle is now live. Please submit your self-appraisal.",
                readStatus = false,
                deliveryChannel = "IN_APP",
                triggeredAt = OffsetDateTime.now().minusMinutes(15)
            )
        )
    }

    fun markAlertAsRead(alertId: UUID) {
        // Mock execution for mutating state to flag alert recognition
    }
}
