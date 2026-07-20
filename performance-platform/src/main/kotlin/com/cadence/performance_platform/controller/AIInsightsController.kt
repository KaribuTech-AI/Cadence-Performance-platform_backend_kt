package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.AIInsightsService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users/{userId}/ai-insights")
class AIInsightsController(private val aiInsightsService: AIInsightsService) {

    @GetMapping
    fun getUserInsights(
        @PathVariable userId: UUID
    ): ResponseEntity<AIInsightListResponse> {
        return ResponseEntity.ok(aiInsightsService.listUserInsights(userId))
    }

    @PostMapping("/generate")
    fun generateInsights(
        @PathVariable userId: UUID,
        @Valid @RequestBody request: AIInsightGenerationRequest
    ): ResponseEntity<AIInsightGenerationResponse> {
        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(aiInsightsService.triggerInsightGeneration(userId, request))
    }
}
