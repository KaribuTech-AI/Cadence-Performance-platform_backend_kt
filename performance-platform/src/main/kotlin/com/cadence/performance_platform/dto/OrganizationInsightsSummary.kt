package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OrganizationInsightsSummary(
    @JsonProperty("averagePerformanceScore") val averagePerformanceScore: Double,
    @JsonProperty("goalCompletionRate") val goalCompletionRate: Double, // Percentage e.g. 74.5
    @JsonProperty("totalActiveReviews") val totalActiveReviews: Int
)
