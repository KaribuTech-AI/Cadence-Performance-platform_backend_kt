package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.GoalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class GoalController(private val goalService: GoalService) {

    @GetMapping("/users/{userId}/goals")
    fun getUserGoals(
        @PathVariable userId: UUID,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedGoalResponse> {
        return ResponseEntity.ok(goalService.getUserGoals(userId, page, size))
    }

    @PostMapping("/users/{userId}/goals")
    fun createGoal(
        @PathVariable userId: UUID,
        @RequestBody request: GoalCreateRequest
    ): ResponseEntity<GoalResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.createGoal(userId, request))
    }

    @PatchMapping("/goals/{goalId}/key-results/{krId}/progress")
    fun updateKeyResultProgress(
        @PathVariable goalId: UUID,
        @PathVariable krId: UUID,
        @RequestBody request: GoalProgressUpdateRequest
    ): ResponseEntity<GoalResponse> {
        return ResponseEntity.ok(goalService.updateKeyResultProgress(goalId, krId, request))
    }

    @PostMapping("/goals/{goalId}/submit")
    fun submitGoalForApproval(
        @PathVariable goalId: UUID
    ): ResponseEntity<GoalResponse> {
        return ResponseEntity.ok(goalService.submitGoalForApproval(goalId))
    }

    @PostMapping("/goals/{goalId}/approval")
    fun handleGoalApprovalWorkflow(
        @PathVariable goalId: UUID,
        @RequestBody request: GoalApprovalRequest
    ): ResponseEntity<GoalResponse> {
        return ResponseEntity.ok(goalService.handleGoalApprovalWorkflow(goalId, request))
    }
}
