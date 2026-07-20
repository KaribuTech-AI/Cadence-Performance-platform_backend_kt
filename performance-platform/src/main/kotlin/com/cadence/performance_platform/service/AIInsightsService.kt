package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class AIInsightsService {

    fun listUserInsights(userId: UUID): AIInsightListResponse {
        val mockInsight = AIInsightResponse(
            insightId = UUID.randomUUID(),
            userId = userId,
            insightType = "PERFORMANCE_TREND",
            summary = "Consistent upward trajectory in leadership and cross-functional ownership.",
            details = "Analysis across the last two review cycles indicates a strong pattern of mentoring junior engineers and leading complex architectural decisions, aligning well with Senior Engineer competencies.",
            confidenceScore = 0.94,
            generatedAt = OffsetDateTime.now().minusDays(1)
        )
        return AIInsightListResponse(
            insights = listOf(mockInsight),
            totalInsights = 1
        )
    }

    fun triggerInsightGeneration(userId: UUID, request: AIInsightGenerationRequest): AIInsightGenerationResponse {
        return AIInsightGenerationResponse(
            trackingId = UUID.randomUUID(),
            status = "PROCESSING",
            estimatedDurationSeconds = 8,
            requestedAt = OffsetDateTime.now()
        )
    }
}
