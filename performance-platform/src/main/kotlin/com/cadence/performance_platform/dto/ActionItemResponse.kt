package com.cadence.performance_platform.dto

import java.time.LocalDate
import java.util.UUID

data class ActionItemResponse(
    val actionItemId: UUID,
    val description: String,
    val dueDate: LocalDate?,
    val isCompleted: Boolean
)
