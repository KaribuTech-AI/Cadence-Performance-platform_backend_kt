package com.cadence.performance_platform.dto

import java.util.UUID

data class PositionResponse(
    val positionId: UUID,
    val title: String,
    val code: String,
    val jobGradeId: UUID?,
    val status: String
)