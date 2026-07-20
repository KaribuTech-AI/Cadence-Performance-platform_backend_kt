package com.cadence.performance_platform.dto

import java.util.UUID

data class TemplateSectionResponse(
    val sectionId: UUID,
    val title: String,
    val type: String, // e.g., COMPETENCY, GOAL, FREE_TEXT
    val weight: Double // Percentage out of 100.0
)
