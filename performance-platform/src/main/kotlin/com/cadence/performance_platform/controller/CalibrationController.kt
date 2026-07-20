package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.CalibrationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/calibration")
class CalibrationController(private val calibrationService: CalibrationService) {

    @PostMapping("/sessions")
    fun createCalibrationSession(
        @RequestBody request: CalibrationSessionCreateRequest
    ): ResponseEntity<CalibrationSessionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(calibrationService.createSession(request))
    }

    @PutMapping("/sessions/{sessionId}/appraisals/{appraisalId}/override")
    fun overrideAppraisalScore(
        @PathVariable sessionId: UUID,
        @PathVariable appraisalId: UUID,
        @RequestHeader("X-Moderator-Id") moderatorId: UUID,
        @RequestBody request: ScoreOverrideRequest
    ): ResponseEntity<CalibrationOverrideResponse> {
        return ResponseEntity.ok(calibrationService.applyScoreOverride(sessionId, appraisalId, moderatorId, request))
    }

    @PostMapping("/sessions/{sessionId}/conclude")
    fun concludeCalibrationSession(
        @PathVariable sessionId: UUID
    ): ResponseEntity<CalibrationSessionResponse> {
        return ResponseEntity.ok(calibrationService.closeSession(sessionId))
    }
}
