package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.ReviewCycleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/review-cycles")
class ReviewCycleController(private val reviewCycleService: ReviewCycleService) {

    @GetMapping
    fun getAllReviewCycles(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedReviewCycleResponse> {
        return ResponseEntity.ok(reviewCycleService.getReviewCycles(page, size))
    }

    @PostMapping
    fun createReviewCycle(
        @RequestBody request: ReviewCycleCreateRequest
    ): ResponseEntity<ReviewCycleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewCycleService.createReviewCycle(request))
    }

    @PatchMapping("/{cycleId}")
    fun updateReviewCycle(
        @PathVariable cycleId: UUID,
        @RequestBody request: ReviewCycleUpdateRequest
    ): ResponseEntity<ReviewCycleResponse> {
        return ResponseEntity.ok(reviewCycleService.updateReviewCycle(cycleId, request))
    }

    @PostMapping("/{cycleId}/publish")
    fun publishReviewCycle(
        @PathVariable cycleId: UUID
    ): ResponseEntity<ReviewCycleResponse> {
        return ResponseEntity.ok(reviewCycleService.publishReviewCycle(cycleId))
    }
}
