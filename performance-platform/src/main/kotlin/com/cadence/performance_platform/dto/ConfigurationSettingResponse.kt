package com.cadence.performance_platform.dto

import java.time.OffsetDateTime

data class ConfigurationSettingResponse(
    val configKey: String, // e.g., platform.scoring.max-scale
    val configValue: String, // e.g., "5.0"
    val description: String?,
    val groupName: String, // e.g., CORE, NOTIFICATIONS, CALIBRATION
    val updatedBy: java.util.UUID,
    val updatedAt: OffsetDateTime
)
