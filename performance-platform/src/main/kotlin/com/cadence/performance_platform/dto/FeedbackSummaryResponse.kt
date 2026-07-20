package com.cadence.performance_platform.dto

import java.util.UUID

data class FeedbackSummaryResponse(
    val userId: UUID,
    val totalResponsesReceived: Int,
    val sectionAverages: Map<String, Double>, // e.g., {"Leadership": 4.2, "Communication": 4.7}
    val openEndedInsights: List<String>, // Anonymized text blocks processed by compliance/AI filters
    val isAnonymityThresholdMet: Boolean // True only if threshold (e.g., >= 3 reviewers) is satisfied
)
