package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.ReviewAppraisalService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/appraisals")
class ReviewAppraisalController(private val appraisalService: ReviewAppraisalService) {

    @GetMapping("/team-reviews")
    fun getTeamAppraisals(
        @RequestParam managerId: UUID,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedReviewAppraisalResponse> {
        return ResponseEntity.ok(appraisalService.getAppraisalsForManager(managerId, page, size))
    }

    @PostMapping("/{appraisalId}/self-submit")
    fun submitSelfAppraisal(
        @PathVariable appraisalId: UUID,
        @RequestBody request: AppraisalSubmitRequest
    ): ResponseEntity<ReviewAppraisalResponse> {
        return ResponseEntity.ok(appraisalService.submitSelfAppraisal(appraisalId, request))
    }

    @PostMapping("/{appraisalId}/manager-submit")
    fun submitManagerEvaluation(
        @PathVariable appraisalId: UUID,
        @RequestBody request: AppraisalSubmitRequest
    ): ResponseEntity<ReviewAppraisalResponse> {
        return ResponseEntity.ok(appraisalService.submitManagerEvaluation(appraisalId, request))
    }

    @PostMapping("/{appraisalId}/sign-off")
    fun completeSignOff(
        @PathVariable appraisalId: UUID,
        @RequestParam role: String, // EMPLOYEE or MANAGER
        @RequestBody request: AppraisalSignOffRequest
    ): ResponseEntity<ReviewAppraisalResponse> {
        return ResponseEntity.ok(appraisalService.executeSignOffWorkflow(appraisalId, role, request))
    }
}
