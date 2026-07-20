package com.cadence.performance_platform.dto

import java.util.UUID

data class KeyResultResponse(
    val keyResultId: UUID,
    val title: String,
    val initialValue: Double,
    val targetValue: Double,
    val currentValue: Double,
    val unit: String // e.g., PERCENTAGE, CURRENCY, COUNT
)
