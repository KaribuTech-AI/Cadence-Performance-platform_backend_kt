package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class SystemAlertResponse(
    val alertId: UUID,
    val targetUserId: UUID,
    val title: String,
    val briefDescription: String,
    val readStatus: Boolean,
    val deliveryChannel: String, // e.g., IN_APP, EMAIL, SMS
    val triggeredAt: OffsetDateTime
)