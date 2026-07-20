package com.cadence.performance_platform.dto

data class AIInsightListResponse(
    val insights: List<AIInsightResponse>,
    val totalInsights: Int
)
