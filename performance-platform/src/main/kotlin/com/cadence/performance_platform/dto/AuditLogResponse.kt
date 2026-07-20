package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class AuditLogResponse(
    val logId: UUID,
    val principalId: UUID, // The user performing the action
    val actionType: String, // e.g., OVERRIDE_SCORE, PUBLISH_CYCLE, DELETE_TEMPLATE
    val targetResource: String, // e.g., APPRAISAL, GOAL, COMPETENCY
    val resourceId: UUID,
    val ipAddress: String,
    val changeSummary: String,
    val timestamp: OffsetDateTime
)
