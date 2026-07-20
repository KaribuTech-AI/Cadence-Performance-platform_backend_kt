package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class CheckinResponse(
    val checkinId: UUID,
    val managerId: UUID,
    val employeeId: UUID,
    val scheduledAt: OffsetDateTime,
    val status: String,
    val agenda: String?
)
