package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.AnalyticsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/analytics")
class AnalyticsController(private val analyticsService: AnalyticsService) {

    @GetMapping("/distribution/{cycleId}")
    fun getDistribution(
        @PathVariable cycleId: UUID
    ): ResponseEntity<PerformanceDistributionResponse> {
        return ResponseEntity.ok(analyticsService.getPerformanceDistribution(cycleId))
    }

    @GetMapping("/talent-matrix")
    fun getTalentMatrix(
        @RequestParam(required = false) departmentId: UUID?
    ): ResponseEntity<List<TalentMatrixResponse>> {
        return ResponseEntity.ok(analyticsService.getTalentMatrixPlacement(departmentId))
    }

    @GetMapping("/organization-summary")
    fun getOrgSummary(): ResponseEntity<OrganizationInsightsSummary> {
        return ResponseEntity.ok(analyticsService.getOrganizationSummary())
    }
}
