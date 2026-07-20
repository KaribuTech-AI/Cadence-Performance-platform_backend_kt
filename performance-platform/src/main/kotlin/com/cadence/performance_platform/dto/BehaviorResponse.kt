package com.cadence.performance_platform.dto

import java.util.UUID

data class BehaviorResponse(
    val behaviorId: UUID,
    val description: String,
    val expectedLevel: Int
)
