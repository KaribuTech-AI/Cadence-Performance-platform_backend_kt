package com.cadence.performance_platform.dto

import java.time.LocalDate
import java.util.UUID

data class GoalDetailResponse(
    val goalId: UUID,
    val title: String,
    val description: String?,
    val status: String,
    val progress: Double,
    val targetDate: LocalDate,
    val parentId: UUID?,
    val keyResults: List<KeyResultResponse>,
    val updatesLog: List<GoalUpdateResponse>
)
