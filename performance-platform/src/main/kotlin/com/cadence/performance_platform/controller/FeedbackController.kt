package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.FeedbackService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class FeedbackController(private val feedbackService: FeedbackService) {

    @GetMapping("/users/{userId}/feedback")
    fun getUserFeedback(
        @PathVariable userId: UUID,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedFeedbackResponse> {
        return ResponseEntity.ok(feedbackService.getUserFeedbackWall(userId, page, size))
    }

    @PostMapping("/feedback")
    fun submitFeedback(
        @RequestHeader("X-User-Id") senderId: UUID, // Simplified identity transfer for skeleton testing
        @RequestBody request: FeedbackCreateRequest
    ): ResponseEntity<FeedbackResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.sendFeedback(senderId, request))
    }

    @DeleteMapping("/feedback/{feedbackId}")
    fun removeFeedback(
        @PathVariable feedbackId: UUID
    ): ResponseEntity<Void> {
        feedbackService.deleteFeedback(feedbackId)
        return ResponseEntity.noContent().build()
    }
}
