package com.cadence.performance_platform.dto

import java.time.OffsetDateTime

data class FeatureToggleResponse(
    val toggleKey: String, // e.g., feature.calibration.enabled
    val isEnabled: Boolean,
    val strategy: String, // e.g., GLOBAL, DEPARTMENTAL
    val updatedAt: OffsetDateTime
)
