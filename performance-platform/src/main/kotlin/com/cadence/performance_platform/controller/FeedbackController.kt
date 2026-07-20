package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.FeedbackService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class FeedbackController(private val feedbackService: FeedbackService) {

    @GetMapping("/feedback/requests")
    fun getFeedbackRequests(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedFeedbackRequestResponse> {
        return ResponseEntity.ok(feedbackService.listFeedbackRequests(page, size))
    }

    @PostMapping("/feedback/requests")
    fun requestFeedback(@Valid @RequestBody request: FeedbackRequestCreateRequest): ResponseEntity<BulkOperationResponse> {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(feedbackService.requestFeedback(request))
    }

    @GetMapping("/feedback/requests/{requestId}")
    fun getQuestionnaire(@PathVariable requestId: UUID): ResponseEntity<FeedbackRequestDetailResponse> {
        return ResponseEntity.ok(feedbackService.getQuestionnaire(requestId))
    }

    @PostMapping("/feedback/requests/{requestId}/responses")
    fun submitFeedback(
        @PathVariable requestId: UUID,
        @Valid @RequestBody request: FeedbackSubmissionRequest
    ): ResponseEntity<FeedbackSubmissionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.submitFeedback(requestId, request))
    }

    @GetMapping("/users/{userId}/feedback-summary")
    fun getFeedbackSummary(@PathVariable userId: UUID): ResponseEntity<FeedbackSummaryResponse> {
        return ResponseEntity.ok(feedbackService.getAggregatedFeedback(userId))
    }
}