package com.cadence.performance_platform.dto

import java.util.UUID

data class GoalSummaryResponse(
    val userId: UUID,
    val totalGoalsCount: Int,
    val activeGoalsCount: Int,
    val completedGoalsCount: Int,
    val overallCompletionPercentage: Double
)
