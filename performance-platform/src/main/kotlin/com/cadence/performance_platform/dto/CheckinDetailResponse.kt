package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class CheckinDetailResponse(
    val checkinId: UUID,
    val managerId: UUID,
    val employeeId: UUID,
    val scheduledAt: OffsetDateTime,
    val status: String,
    val agenda: String?,
    val notes: String?,
    val actionItems: List<ActionItemResponse>
)
