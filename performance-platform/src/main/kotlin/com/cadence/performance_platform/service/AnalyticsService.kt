package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AnalyticsService {

    fun getPerformanceDistribution(cycleId: UUID): PerformanceDistributionResponse {
        return PerformanceDistributionResponse(
            cycleId = cycleId,
            totalCount = 250,
            distributionBuckets = mapOf(
                "Needs Improvement (1.0 - 2.4)" to 12,
                "Meets Expectations (2.5 - 3.9)" to 168,
                "Exceeds Expectations (4.0 - 5.0)" to 70
            )
        )
    }

    fun getTalentMatrixPlacement(departmentId: UUID?): List<TalentMatrixResponse> {
        return listOf(
            TalentMatrixResponse(
                employeeId = UUID.randomUUID(),
                fullName = "Sipho Khumalo",
                performanceScore = 4.7,
                potentialScore = 4.8,
                placementGrid = "Star / High Flyer"
            ),
            TalentMatrixResponse(
                employeeId = UUID.randomUUID(),
                fullName = "Lerato Mokoena",
                performanceScore = 3.8,
                potentialScore = 4.2,
                placementGrid = "High Potential / Core Professional"
            )
        )
    }

    fun getOrganizationSummary(): OrganizationInsightsSummary {
        return OrganizationInsightsSummary(
            averagePerformanceScore = 3.65,
            goalCompletionRate = 78.3,
            totalActiveReviews = 1420
        )
    }
}
